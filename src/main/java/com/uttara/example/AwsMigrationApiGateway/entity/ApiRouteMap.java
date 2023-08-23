package com.uttara.example.AwsMigrationApiGateway.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class  ApiRouteMap {
    @Id
    @Column(name="api_end_point_name")
    private String apiEndPointName;
    @Column(name="aws_route_uri")
    private String awsRouteUri;
    @Column(name="ngdc_route_uri")
    private String ngdcRouteUri;
}
