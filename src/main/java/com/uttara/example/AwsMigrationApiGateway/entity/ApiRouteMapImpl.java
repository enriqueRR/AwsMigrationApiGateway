package com.uttara.example.AwsMigrationApiGateway.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table
public class ApiRouteMapImpl implements ApiRouteMap {
    @Id
    @Column(name = "api_end_point_name")
    private String apiEndPointName;
    @Column(name = "aws_route_uri")
    private String awsRouteUri;
    @Column(name = "ngdc_route_uri")
    private String ngdcRouteUri;

    @Override
    public String getApiEndPointName() {
        return apiEndPointName;
    }

    @Override
    public String getAwsRouteUri() {
        return awsRouteUri;
    }

    @Override
    public String getNgdcRouteUri() {
        return ngdcRouteUri;
    }
}
