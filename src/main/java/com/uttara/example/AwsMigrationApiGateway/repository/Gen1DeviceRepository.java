package com.uttara.example.AwsMigrationApiGateway.repository;


import com.uttara.example.AwsMigrationApiGateway.common.Shard;
import com.uttara.example.AwsMigrationApiGateway.entity.ApiRouteMap;
import com.uttara.example.AwsMigrationApiGateway.entity.Device;
import com.uttara.example.AwsMigrationApiGateway.entity.ShardImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface Gen1DeviceRepository extends JpaRepository<Device,String> {

    // custom query to search to blog post by title or content
    Device findByDeviceId(String deviceId);
    Device findDeviceByDeviceEmailId(String deviceEmailId);

    @Query(nativeQuery = true, value = "SELECT * FROM Shard s WHERE s.code = :shardCode")
    Shard findHostNameByShardCode(String shardCode);

    @Query(nativeQuery = true, value = "SELECT * FROM ApiRouteMap s WHERE s.api_end_point_name = :apiEndPointName")
    ApiRouteMap findRouteUri(String apiEndPointName);

}