package com.uttara.example.AwsMigrationApiGateway.filter;

import com.uttara.example.AwsMigrationApiGateway.service.LaunchDarklyClient;
import com.uttara.example.AwsMigrationApiGateway.utility.Parsing;
import com.uttara.example.AwsMigrationApiGateway.utility.TsApiGatewayConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static com.uttara.example.AwsMigrationApiGateway.utility.TsApiGatewayConstants.*;

@Component
public class PayLoadExtractFilter implements GlobalFilter, Ordered {

    final Logger logger = LoggerFactory.getLogger(PayLoadExtractFilter.class);
    private final List<HttpMessageReader<?>> messageReaders = getMessageReaders();

    private List<HttpMessageReader<?>> getMessageReaders() {
        return HandlerStrategies.withDefaults().messageReaders();
    }

    @Autowired
    private LaunchDarklyClient launchDarklyClient;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("---PayLoadExtractFilter----");
        String uriPath = exchange.getRequest().getURI().getPath();
        // extracting the deviceId,deviceEmailId,printerID,JobId from payload
        // Only apply on POST requests
        if(launchDarklyClient.getFlagValueNgdc() || launchDarklyClient.getFlagValueAws()) {
            return chain.filter(exchange);
        }
        else if (HttpMethod.POST.equals(exchange.getRequest().getMethod()))
        {
            return logRequestBody(exchange, chain);
        }
        else
        {
             /*
             * if block will be executed for onramp method-type GET,PUT requests
             */
            if (uriPath.startsWith(ONRAMP))
            {
                if (exchange.getRequest().getURI().getPath().equals(ONRAMP+JOBS))
                {
                    // method type=GET /jobs/ ===>jobs
                    return chain.filter(exchange);
                }
                if (exchange.getRequest().getURI().getPath().equals(ONRAMP+PRINT_JOB_URI))
                {
                    // method type=GET  /jobs/printjobs/ ===>printJobs
                    return chain.filter(exchange);
                }
                if (exchange.getRequest().getURI().getPath().contains(PRINT_JOB_URI))
                {
                    // method type=PUT /jobs/printjobs/{JOBID}/cancel/ ===>cancelPrint
                    // method type=PUT /jobs/printjobs/{JOBID}/documents/{DOCUMENTID}/data/ ===>data
                    // method type=GET /jobs/printjobs/{JOBID}/documents/{DOCUMENTID}/processingelements/ ==>documentProcessingElement
                    // method type=PUT /jobs/printjobs/{JOBID}/documents/{DOCUMENTID}/processingelements/ ==>documentProcessingElement
                    // method type=GET /jobs/printjobs/{JOBID}/documents/{DOCUMENTID}/ ===>document
                    // method type=DELETE /jobs/printjobs/{JOBID}/documents/{DOCUMENTID}/ ===>document
                    // method type=GET  /jobs/printjobs/{JOBID}/documents/ ===>documents
                    // method type=GET  /jobs/printjobs/{JOBID}/documents/{DOCUMENTID}/status/ ===>documentStatus
                    // method type=GET  /jobs/printjobs/{JOBID}/documents/{DOCUMENTID}/output/ ==>output
                    // method type=GET  /jobs/printjobs/{JOBID}/processingelements/ ===>printJobProcessingElements
                    // method type=PUT  /jobs/printjobs/{JOBID}/processingelements/ ===>printJobProcessingElements
                    // method type=GET  /jobs/printjobs/{JOBID}/ ===>printJob
                    // method type=GET /jobs/printjobs/{JOBID}/status ===>printJobStatus
                    // method type=GET /jobs/printjobs/{JOBID}/documents/{DOCUMENTID}/preview/ ==>preview
                    // method type=PUT /jobs/printjobs/{JOBID}/documents/{DOCUMENTID}/preview/ ==>preview
                    // method type=GET /jobs/printjobs/{JOBID}/documents/{DOCUMENTID}/preview/processingelements/ ===>previewProcessingElement
                    // method type=PUT /jobs/printjobs/{JOBID}/documents/{DOCUMENTID}/preview/processingelements/ ===>previewProcessingElement
                    // method type=GET /jobs/printjobs/{JOBID}/documents/{DOCUMENTID}/preview/status ===>previewstatus
                    // method type=GET /jobs/printjobs/{JOBID}/documents/{DOCUMENTID}/preview/output ===>previewoutput
                    String jobId = findValueFromUri(uriPath, 4);
                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().headers(h -> h.set(JOB_ID, jobId)).build();
                    ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                    return chain.filter(modifiedExchange);
                }

                if (uriPath.contains(VERSION))
                {
                    // method type=GET /version ===>cpgVersion
                    return chain.filter(exchange);
                }
                if (uriPath.contains(SCAN_JOB_URI))
                {
                    // method type=PUT /jobs/scanjobs/{JOBID}/cancel/ ===>cancelScan
                    // method type=GET /jobs/scanjobs/{JOBID}/documents/{DOCUMENTID}/output/ ===>scanJobOutput
                    String jobId = findValueFromUri(uriPath, 4);
                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().headers(h -> h.set(JOB_ID, jobId)).build();
                    ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                    return chain.filter(modifiedExchange);
                }
                if (uriPath.contains(DELIVERY_ONLY_JOB_URI))
                {
                    // method type=GET /jobs/deliveryonlyjobs/{JOBID}/status ===>deliveryOnlyJobStatus
                    // method type=GET /jobs/deliveryonlyjobs/{JOBID}/ ===>deliveryOnlyJob
                    // method type=GET /jobs/deliveryonlyjobs/{JOBID}/documents/{DOCUMENTID}/status/ ===>documentStatus
                    // method type=GET /jobs/deliveryonlyjobs/{JOBID}/documents/{DOCUMENTID}/ ===>deliveryOnlyJobDocument
                    // method type=PUT /jobs/deliveryonlyjobs/{JOBID}/documents/{DOCUMENTID}/ ===>deliveryOnlyJobDocument
                    // method type=DELETE /jobs/deliveryonlyjobs/{JOBID}/documents/{DOCUMENTID}/ ===>deliveryOnlyJobDocument
                    // method type=PUT /jobs/deliveryonlyjobs/{JOBID}/cancel/ ===>cancelPrint
                    String jobId = findValueFromUri(uriPath, 4);
                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().headers(h -> h.set(JOB_ID, jobId)).build();
                    ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                    return chain.filter(modifiedExchange);
                }
                // method type=GET /devices/printers/  ===>printer
                if (uriPath.contains(DEVICE_JOB_URI))
                {
                    // method type=GET /devices/printers/{PrinterID}/scansupportedprocessingelements/ ===>scansupportedprocessingelements
                    // method type=GET /devices/printers/{PrinterID}/ ===>printer
                    // method type=PUT /devices/printers/{PrinterID}/ ===>printe
                    // method type=GET /devices/printers/{PrinterID}/status/  ===>printerStatus
                    // method type=GET /devices/printers/{PrinterID}/emailaddress/  ===>emailAddress
                    // method type=GET /devices/  ===>devices
                    // method type=GET /devices/printers/{PrinterID}/supportedprocessingelements/  ===>printerSupportedProcessingElements
                    String printerId = findValueFromUri(uriPath, 4);
                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().headers(h -> h.set(PRINTER_CLOUD_ID, printerId)).build();
                    ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                    return chain.filter(modifiedExchange);
                }
                //onramp /jobs/renderjobs/
                if (uriPath.contains(RENDER_JOB_URI))
                {
                    //fine
                    // method type=PUT /jobs/renderjobs/{JOBID}/cancel/ ===>cancelrender
                    // method type=PUT /jobs/renderjobs/{JOBID}/documents/{DOCUMENTID}/data/ ===>renderData
                    // method type=GET /jobs/renderjobs/{JOBID}/processingelements/ ===>renderJobProcessingElement
                    // method type=GET /jobs/renderjobs/{JOBID}/documents/{DOCUMENTID}/processingelements/ ===>renderDocumentProcessingElement
                    // method type=PUT /jobs/renderjobs/{JOBID}/documents/{DOCUMENTID}/processingelements/ ===>renderDocumentProcessingElement
                    // method type=GET /jobs/renderjobs/{JOBID}/documents/{DOCUMENTID}/ ===>renderDocument
                    // method type=DELETE /jobs/renderjobs/{JOBID}/documents/{DOCUMENTID}/ ===>renderDocument
                    // method type=PUT /jobs/renderjobs/{JOBID}/documents/ ===>renderDocuments
                    // method type=GET /jobs/renderjobs/{JOBID}/documents/{DOCUMENTID}/status/ ===>renderDocumentStatus
                    // method type=GET /jobs/renderjobs/{JOBID}/ ===>renderJobs
                    // method type=POST /jobs/renderjobs/{JOBID}/ ===>renderJobs
                    // method type=GET /jobs/renderjobs/{JOBID}/status ===>renderJobStatus
                    // method type=GET /jobs/renderjobs/{JOBID}/documents/{DOCUMENTID}/output/ ===>renderOutput
                    // method type=POST /jobs/renderjobs/{JOBID}/render/ ===>renderOutput
                    String jobId = findValueFromUri(uriPath, 4);
                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().headers(h -> h.set(JOB_ID, jobId)).build();
                    ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                    return chain.filter(modifiedExchange);
                }
                if (uriPath.contains(SCERET_TOKEN))
                {
                    // method type=GET /tokens/session/secret ===>sessionSecret
                    return chain.filter(exchange);
                }
                if(uriPath.contains(SCANNER_CAPABILITIES))
                {
                    //method type=GET /printers/{PrinterID}/ScannerCapabilities ===>scannerCapabilities
                    String printerId = findValueFromUri(uriPath, 3);
                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().headers(h -> h.set(PRINTER_CLOUD_ID, printerId)).build();
                    ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                    return chain.filter(modifiedExchange);
                }
                if(uriPath.contains(SCANNER_STATUS))
                {
                    //method type=GET /printers/{PrinterID}/ScannerStatus ===>eSCLScanStatus
                    String printerId = findValueFromUri(uriPath, 3);
                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().headers(h -> h.set(PRINTER_CLOUD_ID, printerId)).build();
                    ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                    return chain.filter(modifiedExchange);
                }

                if(uriPath.contains(SCAN_JOBS))
                {
                    //method type POST  /printers/{PrinterID}/ScanJobs ===>scanJobs
                    //method type DELETE  /printers/{PrinterID}/ScanJobs/{JOBID} ===>cancelESCLScan
                    //method type GET  /printers/{PrinterID}/ScanJobs/{JOBID}/documents/{DOCUMENTID} ===>eSCLScanOutputDocument
                    //method type GET /printers/{PrinterID}/ScanJobs/{JOBID}/NextDocument ===>eSCLScanNextDocument
                    String jobId = findValueFromUri(uriPath, 5);
                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().headers(h -> h.set(JOB_ID, jobId)).build();
                    ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                    return chain.filter(modifiedExchange);
                }
                //method type GET prepapi/validate ===>prePAPIValidate
                return chain.filter(exchange);
            }
            /*
             * if block will be executed for eprintcenter method-type GET,PUT requests
             */
            if (uriPath.startsWith(EPRINT_CENTER))
            {
                 if(uriPath.contains(PRINT_JOB_URI))
                 {
                    //method type PUT  /jobs/printjobs/{JobID}/cancel/ ===>cancelPrint
                     String jobId = findValueFromUri(uriPath, 4);
                     ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().headers(h -> h.set(JOB_ID, jobId)).build();
                     ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                     return chain.filter(modifiedExchange);
                }
                 if (uriPath.contains("/devices/printers"))
                {
                    //method type GET  /devices/printers ===>printer
                    //method type PUT  /devices/printers ===>printer
                    //method type POST  /devices/printers===>printer
                    //method type DELETE  /devices/printers ===>printer
                    return chain.filter(exchange);
                }
                if (uriPath.contains(VERSION_))
                {
                    //method type GET  /version/ ===>GET
                    return chain.filter(exchange);
                }
                 if (uriPath.contains(DEVICE_JOB_URI))
                 {
                     //method type GET /devices/printers/{optinParameter}/optin/suppliesinfo/ ===>optinSuppliesInfo
                     //method type PUT /devices/printers/{optinParameter}/optin/suppliesinfo/ ===>optinSuppliesInfo
                     //method type DELETE /devices/printers/{optinParameter}/optin/suppliesinfo/ ===>optinSuppliesInfo
                    //method type PUT  /devices/printers/{PrinterID}/cloudconfiguration/ ===>cloudConfiguration
                    //method type GET  /devices/printers/{PrinterID}/cloudconfiguration/ ===>cloudConfiguration
                    //method type GET  /devices/printers/{PrinterID}/emailaddress/===>emailAddress
                    //method type PUT  /devices/printers/{PrinterID}/emailaddress/===>emailAddress
                    //method type DELETE  /devices/printers/{PrinterID}/emailaddress/===>emailAddress
                     //method type GET  /devices/printers/{PrinterID}/ ===>printer
                     //method type PUT  /devices/printers/{PrinterID}/ ===>printer
                     //method type POST  /devices/printers/{PrinterID}/ ===>printer
                     //method type DELETE  /devices/printers/{PrinterID}/ ===>printer
                     //method type GET  /devices/printersDel/{PrinterID}/ ===>printer
                     //method type PUT  /devices/printersDel/{PrinterID}/ ===>printer
                     //method type DELETE  /devices/printersDel/{PrinterID}/ ===>printer
                      //method type GET  /devices/printers/{PrinterID}/status/ ===>printerStatus
                     //method type GET  /devices/printers/{PrinterID}/whitelist/ ===>whiteList
                     //method type POST  /devices/printers/{PrinterID}/whitelist/ ===>whiteList
                     //method type GET /devices/printers/{PrinterID}/defaultprocessingelements/ ===>printerDefaultsProcessingElements
                     //method type PUT /devices/printers/{PrinterID}/defaultprocessingelements/ ===>printerDefaultsProcessingElements
                     //method type GET /devices/printers/{PrinterID}/whitelist/{WhiteListID}/ ===>whiteListEntry
                     //method type PUT /devices/printers/{PrinterID}/whitelist/{WhiteListID}/ ===>whiteListEntry
                     //method type DELETE /devices/printers/{PrinterID}/whitelist/{WhiteListID}/ ===>whiteListEntry
                     //method type GET /devices/printers/{PrinterID}/printjobs/ ===>printJobs
                     //method type GET /devices/printers/{PrinterID}/supportedprocessingelements/ ===>printerSupportedProcessingElements
                     //method type GET /devices/printers/{PrinterID}/owner/ ===>printerOwner
                     //method type GET /devices/printers/{PrinterID}/optin/ ===>optInConfig
                     //method type PUT /devices/printers/{PrinterID}/optin/ ===>optInConfig
                     //method type GET /devices/printers/{PrinterID}/consumablesubscription/ ===>consumableSubscriptionConfig
                     //method type PUT /devices/printers/{PrinterID}/consumablesubscription/ ===>consumableSubscriptionConfig
                     //method type GET /devices/printers/{PrinterID}/optin/inklevel/ ===>optinInkLevel
                     //method type PUT /devices/printers/{PrinterID}/optin/inklevel/ ===>optinInkLevel
                     //method type DELETE /devices/printers/{PrinterID}/optin/inklevel/ ===>optinInkLevel
                     //method type GET /devices/printers/{PrinterID}/optin/cDCA ===>optinCDCA
                     //method type PUT /devices/printers/{PrinterID}/optin/cDCA ===>optinCDCA
                     //method type DELETE /devices/printers/{PrinterID}/optin/cDCA ===>optinCDCA
                     //method type GET /devices/printers/{PrinterID}/blacklist/ ===>blackListsFinder
                     //method type POST /devices/printers/{PrinterID}/blacklist/ ===>blackListsFinder
                     //method type GET /devices/printers/{PrinterID}/printerwhitelist/ ===>whiteListFinder
                     //method type POST /devices/printers/{PrinterID}/printerwhitelist/ ===>whiteListFinder
                     //method type GET /devices/printers/{PrinterID}/blacklist/{BLACK_LIST_ENTRY} ===>blackListFinder
                    String printerId = findValueFromUri(uriPath, 4);
                     ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().headers(h -> h.set(PRINTER_CLOUD_ID, printerId)).build();
                    ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                    return chain.filter(modifiedExchange);
                }
                if (uriPath.contains(V2+DEVICE_JOB_URI))
                {
                    //method type GET /v2/devices/printers/{PrinterID}/optin/{optinType} ===>solutionOptIn
                    //method type PUT /v2/devices/printers/{PrinterID}/optin/{optinType} ===>solutionOptIn
                    //method type DELETE /v2/devices/printers/{PrinterID}/optin/{optinType} ===>solutionOptIn
                    //method type GET /v2/devices/printers/{optinType}/schedules ===>optinSchedule--------yet to work
                    String printerId = findValueFromUri(uriPath, 5);
                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().headers(h -> h.set(PRINTER_CLOUD_ID, printerId)).build();
                    ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                    return chain.filter(modifiedExchange);
                }
                //method type GET  /ownerships ===>ownerShips
                //method type PUT  /ownerships ===>ownerShips
                //method type GET /ownerships/{OwnershipID}/ ===>ownerShip
                //method type DELETE /ownerships/{OwnershipID}/ ===>ownerShip
                if (uriPath.contains(OWNER_SHIP))
                {
                    String ownershipID = findValueFromUri(uriPath, 3);
                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().headers(h -> h.set(OWNER_SHIP_ID, ownershipID)).build();
                    ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                    return chain.filter(modifiedExchange);
                }
                return chain.filter(exchange);
            }

           /**
             * if block will be executed for offramp  method-type GET,PUT requests
             */
            if (uriPath.startsWith(OFFRAMP))
            {
                if (uriPath.contains(OFFRAMP_PRINTERS))
                {
                    //method type DELETE /Printers/{PrinterID}/ ===>printer
                    //method type GET /Printers/{PrinterID}/EmailAddress ===>emailAddress
                    //method type GET /Printers/{PrinterID}/XMPPConfiguration ===>xMPPConfiguration
                    //method type GET /Printers/{PrinterID}/CloudConfiguration ===>printerCloudConfig
                    //method type GET /Printers/{PrinterID}/CloudConfiguration/V2 ===>printerCloudConfigV2
                    //method type PUT /Printers/{PrinterID}/StateReport ===>printerStateReport
                    //method type GET /Printers/{PrinterID}/ClaimCode ===>claimCode
                    String printerId = findValueFromUri(uriPath, 3);
                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().headers(h -> h.set(PRINTER_CLOUD_ID, printerId)).build();
                    ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                    return chain.filter(modifiedExchange);
                }
                if (uriPath.contains(SCAN_JOB_URI))
                {
                    //method type PUT /jobs/scanjobs/{JOBID}/documents/{DOCUMENTID}/upload/{PAGE} ===>scanJobUpload
                    String jobId = findValueFromUri(uriPath, 4);
                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().headers(h -> h.set(JOB_ID, jobId)).build();
                    ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                    return chain.filter(modifiedExchange);
                }
                if (uriPath.contains(SCERET_TOKEN))
                {
                    //method type GET /tokens/session/secret ===>sessionSecret
                    return chain.filter(exchange);
                }
                if (uriPath.contains(EMAIL_ADDRESS))
                {
                    //method type DELETE /EmailAddress/{EmailAddress}/ ===>printerEmail
                    String deviceEmailId = findValueFromUri(uriPath, 3);
                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().headers(h -> h.set(DEVICE_EMAIL_ID, deviceEmailId)).build();
                    ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                    return chain.filter(modifiedExchange);

                }
                if (uriPath.contains(JOB_OUPUT))
                {
                    //method type HEAD /Printers/PrintJobs/Output/{CACHEID} ===>output
                    String jobId = findValueFromUri(uriPath, 3);
                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().headers(h -> h.set(JOB_ID, jobId)).build();
                    ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                    return chain.filter(modifiedExchange);
                }
            }
            return chain.filter(exchange);
        }
    }

  private Mono<Void> logRequestBody(ServerWebExchange exchange, GatewayFilterChain chain) {

        return DataBufferUtils.join(exchange.getRequest().getBody()).flatMap(dataBuffer -> {
            DataBufferUtils.retain(dataBuffer);
            Flux<DataBuffer> cachedFlux = Flux.defer(() -> Flux.just(dataBuffer.slice(0, dataBuffer.readableByteCount())));
            ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                @Override
                public Flux<DataBuffer> getBody() {
                    return cachedFlux;
                }
            };

            return ServerRequest
                    // must construct a new exchange instance, same as below
                    .create(exchange.mutate().request(mutatedRequest).build(), messageReaders).bodyToMono(String.class).flatMap(body -> {
                        // do what ever you want with this body string, I logged it.
                        String uriPath = exchange.getRequest().getURI().getPath();
                        /*
                         * if block will be executed for onramp method-type POST requests
                         */
                        if (uriPath.startsWith(ONRAMP))
                        {
                            if (body.contains(namespacePrefix)) {
                                String deviceEmailId = Parsing.getDeviceEmailId(body, DEVICE_EMAILID_XPATH);
                                Map<String, String> headers = exchange.getRequest().getHeaders().toSingleValueMap();
                                if (deviceEmailId != null) {
                                    logger.info("deviceEmailId: {}", deviceEmailId);
                                    exchange.getRequest().mutate().headers(h -> h.set(DEVICE_EMAIL_ID, deviceEmailId)).build();
                                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
                                }
                                String deviceId = Parsing.getDeviceEmailId(body, DEVICE_ID_XPATH);
                                if (deviceId != null) {
                                    logger.info("deviceId: {}", deviceId);
                                    exchange.getRequest().mutate().headers(h -> h.set(PRINTER_CLOUD_ID, deviceId)).build();
                                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
                                }
                            }
                            if(uriPath.contains(SCAN_JOBS))
                                {
                                    //method type POST  /printers/{PrinterID}/ScanJobs
                                    String printerId = findValueFromUri(uriPath, 3);
                                    exchange.getRequest().mutate().headers(h -> h.set(PRINTER_CLOUD_ID, printerId)).build();
                                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
                                }

                                if(uriPath.contains(SCANNER_CAPABILITIES))
                                {
                                    //method type=GET /printers/{PrinterID}/ScannerCapabilities ===>scannerCapabilities
                                    String printerId = findValueFromUri(uriPath, 3);
                                    exchange.getRequest().mutate().headers(h -> h.set(PRINTER_CLOUD_ID, printerId)).build();
                                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
                                }

                                if (uriPath.contains(SESSION_TOKEN))
                                {
                                    // method type=POST /tokens/session/token ===>sessionToken
                                    return chain.filter(exchange);
                                }

                                if (uriPath.contains(RENDER_JOB_URI))
                                {
                                    // method type=POST /jobs/renderjobs/{JOBID}/documents/{DOCUMENTID}/ ===>renderDocument
                                    // method type=POST /jobs/renderjobs/{JOBID}/ ===>renderJobs
                                    // method type=POST /jobs/renderjobs/{JOBID}/render/ ===>renderOutput
                                    String jobId = findValueFromUri(uriPath, 4);
                                    exchange.getRequest().mutate().headers(h -> h.set(JOB_ID,jobId)).build();
                                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
                                }
                                if (uriPath.contains(DEVICE_JOB_URI))
                                {
                                    // method type=POST /devices/printers/{PrinterID}/ ===>printer
                                    String printerId = findValueFromUri(uriPath, 4);
                                     exchange.getRequest().mutate().headers(h -> h.set(PRINTER_CLOUD_ID, printerId)).build();
                                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
                                }
                                if (uriPath.contains(DELIVERY_ONLY_JOB_URI))
                                {
                                    // method type=POST /jobs/deliveryonlyjobs/ ===>deliveryOnlyJobs
                                    String deviceEmailId = Parsing.getDeviceEmail(body);
                                    if(deviceEmailId!=null)
                                    {
                                        logger.info("deviceEmailId: {}", deviceEmailId);
                                        exchange.getRequest().mutate().headers(h -> h.set(DEVICE_EMAIL_ID, deviceEmailId)).build();
                                        return chain.filter(exchange.mutate().request(mutatedRequest).build());
                                    }
                                    String deviceId = Parsing.getDeviceId(body);
                                    if(deviceId!=null)
                                    {
                                        logger.info("deviceId: {}", deviceId);
                                        exchange.getRequest().mutate().headers(h -> h.set(PRINTER_CLOUD_ID, deviceId)).build();
                                        return chain.filter(exchange.mutate().request(mutatedRequest).build());
                                    }

                                }
                                if (uriPath.contains(SCAN_JOB_URI))
                                {
                                    // method type=POST /jobs/scanjobs/ ===>scanJob
                                    String deviceEmailId = Parsing.getDeviceEmailID(body);
                                    if(deviceEmailId!=null)
                                    {
                                        logger.info("deviceEmailId: {}", deviceEmailId);
                                        exchange.getRequest().mutate().headers(h -> h.set(DEVICE_EMAIL_ID, deviceEmailId)).build();
                                        return chain.filter(exchange.mutate().request(mutatedRequest).build());
                                    }
                                    String deviceId = Parsing.getDeviceID(body);
                                    if(deviceId!=null)
                                    {
                                        logger.info("deviceId: {}", deviceId);
                                        exchange.getRequest().mutate().headers(h -> h.set(PRINTER_CLOUD_ID, deviceId)).build();
                                        return chain.filter(exchange.mutate().request(mutatedRequest).build());
                                    }
                                }
                            if (exchange.getRequest().getURI().getPath().contains(PRINT_JOB_URI))
                            {
                                // method type=POST  /jobs/printjobs/ ===>printJobs
                                String printerEmailId = Parsing.getDeviceEmail(body);
                                if(printerEmailId !=null)
                                {
                                    logger.info("deviceEmailId: {}", printerEmailId);
                                    exchange.getRequest().mutate().headers(h -> h.set(DEVICE_EMAIL_ID, printerEmailId)).build();
                                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
                                }
                                else
                                {
                                    // method type=POST /jobs/printjobs/{JOBID}/print/ ===>print
                                    // method type=POST /jobs/printjobs/{JOBID}/documents/{DOCUMENTID}/preview/ ==>preview
                                    String jobId = findValueFromUri(uriPath, 4);
                                    exchange.getRequest().mutate().headers(h -> h.set(JOB_ID, jobId)).build();
                                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
                                }
                            }
                        }
                        /*
                         * if block will be executed for eprintcenter method-type POST requests
                         */
                        if (uriPath.startsWith(EPRINT_CENTER))
                        {
                            if (uriPath.contains(DEVICE_JOB_URI))
                            {
                                //method type POST  /devices/printers/{PrinterID}/emailaddress/suggestions/ ===>suggestions
                                //method type POST  /devices/printers/{PrinterID}/emailaddress/availability/ ===>availability
                                //method type POST  /devices/printersDel/{PrinterID}/ ===>printer
                                //method type POST  /devices/printers/{PrinterID}/whitelist/ ===>whiteList
                                //method type POST /devices/printers/{PrinterID}/appplayer/event/ ===>appPlayerEvent
                                //method type POST /devices/printers/{PrinterID}/blacklist/ ===>blackListsFinder
                                //method type POST /devices/printers/{PrinterID}/printerwhitelist/ ===>whiteListFinder
                                //method type POST /devices/printers/{PrinterID}/blacklist/{BLACK_LIST_ENTRY} ===>blackListFinder
                                String printerId = findValueFromUri(uriPath, 4);
                                exchange.getRequest().mutate().headers(h -> h.set(PRINTER_CLOUD_ID, printerId)).build();
                                return chain.filter(exchange.mutate().request(mutatedRequest).build());
                            }
                            if (uriPath.contains(DEVICEPRINTERS))
                            {
                                String deviceEmailId = Parsing.getDeviceEmail(body);
                                if(deviceEmailId!=null)
                                {
                                    logger.info("deviceEmailId: {}", deviceEmailId);
                                    exchange.getRequest().mutate().headers(h -> h.set(DEVICE_EMAIL_ID, deviceEmailId)).build();
                                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
                                }
                                String deviceId = Parsing.getDeviceId(body);
                                if(deviceId!=null)
                                {
                                    logger.info("deviceId: {}", deviceId);
                                    exchange.getRequest().mutate().headers(h -> h.set(PRINTER_CLOUD_ID, deviceId)).build();
                                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
                                }
                            }
                            if (uriPath.contains(OWNER_SHIP+STATUS))
                            {
                                //method type POST  /ownerships/status ===>ownershipStatus
                                String printerEmailId = Parsing.getPrinterEmailId(body);
                                exchange.getRequest().mutate().headers(h -> h.set(DEVICE_EMAIL_ID, printerEmailId)).build();
                                return chain.filter(exchange.mutate().request(mutatedRequest).build());
                            }
                            if (uriPath.contains(OWNER_SHIP))
                            {
                                //method type POST  /ownerships ===>ownerShips
                                String printerEmailId = Parsing.getPrinterEmailId(body);
                                exchange.getRequest().mutate().headers(h -> h.set(DEVICE_EMAIL_ID, printerEmailId)).build();
                                return chain.filter(exchange.mutate().request(mutatedRequest).build());

                            }
                            if (uriPath.contains(DEVICE_JOB_URI+OWNERSHIP))
                            {
                                //method type POST  /devices/printers/ownerships ===>deviceOwnership
                                String printerEmailId = Parsing.getPrinterEmailId(body);
                                exchange.getRequest().mutate().headers(h -> h.set(DEVICE_EMAIL_ID, printerEmailId)).build();
                                return chain.filter(exchange.mutate().request(mutatedRequest).build());
                            }
                            if (uriPath.contains(V1+DEVICE_JOB_URI+OWNERSHIP))
                            {
                                //method type POST /v1/devices/printers/ownerships ===>deviceOwnershipStratus
                                String printerEmailId = Parsing.getPrinterEmailId(body);
                                exchange.getRequest().mutate().headers(h -> h.set(DEVICE_EMAIL_ID, printerEmailId)).build();
                                return chain.filter(exchange.mutate().request(mutatedRequest).build());

                            }
                        }
                        /**
                         * if block will be executed for offramp method-type POST requests
                         */
                        if (uriPath.startsWith(OFFRAMP))
                        {
                            if (uriPath.contains(OFFRAMP_PRINTERS))
                            {
                                //method type POST /Printers/{PrinterID}/EmailAddress ===>emailAddress
                                //method type POST /Printers/{PrinterID}/PrintJobs/Instructions ===>instructions
                                //method type POST /Printers/{PrinterID}/CloudConfiguration ===>printerCloudConfig
                                //method type POST /Printers/{PrinterID}/StateReport ===>printerStateReport
                                //method type POST /Printers/{PrinterID}/CloudSignaling/SignalSession ===>collectionConfig
                                String printerId = findValueFromUri(uriPath, 3);
                                exchange.getRequest().mutate().headers(h -> h.set(PRINTER_CLOUD_ID, printerId)).build();
                                return chain.filter(exchange.mutate().request(mutatedRequest).build());
                            }
                            if (uriPath.contains(SCAN_JOB_URI))
                            {
                                //method type POST /jobs/scanjobs/{JOBID}/documents/{DOCUMENTID}/upload/{PAGE} ===>scanJobUpload
                                String jobId = findValueFromUri(uriPath, 4);
                                exchange.getRequest().mutate().headers(h -> h.set(JOB_ID, jobId)).build();
                                return chain.filter(exchange.mutate().request(mutatedRequest).build());
                            }
                            if (uriPath.contains(PRINTERS))
                            {
                                //method type POST /Printers ===>printers
                                String printerEmailId = Parsing.getPrinterEmailId(body);
                                exchange.getRequest().mutate().headers(h -> h.set(DEVICE_EMAIL_ID, printerEmailId)).build();
                                return chain.filter(exchange.mutate().request(mutatedRequest).build());
                            }
                            if (uriPath.contains(VALIDATE_PRINTER))
                            {
                                //method type POST /ValidatePrinter ===>validatePrinter
                                String printerEmailId = Parsing.getPrinterEmailId(body);
                                exchange.getRequest().mutate().headers(h -> h.set(DEVICE_EMAIL_ID, printerEmailId)).build();
                                return chain.filter(exchange.mutate().request(mutatedRequest).build());
                            }
                        }
                            return chain.filter(exchange);
                    });
        });
    }

    private String findValueFromUri(String path, int segment) {
        String[] segments = path.split("/");
        return segments[segment];
    }

    @Override
    public int getOrder() {
        return -1;
    }
}