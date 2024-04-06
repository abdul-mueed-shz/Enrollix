package com.redmath.institution.common.configuration;
import com.redmath.institution.common.constants.IgniteConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.ClusterState;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.DataRegionConfiguration;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@Slf4j
@Configuration
@Import(FlywayMigration.class)
public class IgniteConfig {

    @Value(value = IgniteConstants.IGNITE_ADDRESS)
    public String tcpDiscoveryAddress;

    @Value(value = IgniteConstants.IGNITE_INSTANCE_NAME)
    public String igniteInstanceName;

    /**
     * igniteInstance.
     */
    @Bean(name = IgniteConstants.IGNITE_INSTANCE_LABEL)
    public Ignite igniteInstance(IgniteConfiguration igniteConfiguration) {

        Ignite ignite = Ignition.start(igniteConfiguration);
        ignite.cluster().state(ClusterState.ACTIVE);
        return ignite;
    }

    /**
     * IgniteConfiguration.
     */
    @Bean
    public IgniteConfiguration igniteConfiguration(
            TcpDiscoveryVmIpFinder tcpDiscoveryVmIpFinder,
            DataStorageConfiguration dataStorageConfiguration,
            CacheConfiguration[] appCacheConfigurations,
            FlywayMigration flywayMigration
    ) {

        flywayMigration.migrate();
        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
        log.info("TCP Discovery Address : " + tcpDiscoveryAddress);
        if (StringUtils.hasText(tcpDiscoveryAddress)) {
            igniteConfiguration.setDiscoverySpi(tcpDiscoverySpi(tcpDiscoveryVmIpFinder));
        }
        log.info("Ignite Instance Name : " + igniteInstanceName);
        igniteConfiguration.setIgniteInstanceName(igniteInstanceName);
        igniteConfiguration.setPeerClassLoadingEnabled(true);
        igniteConfiguration.setDataStorageConfiguration(dataStorageConfiguration);
        igniteConfiguration.setClientMode(true);
        if (appCacheConfigurations.length > 0) {
            igniteConfiguration.setCacheConfiguration(appCacheConfigurations);
        }

        return igniteConfiguration;
    }

    /**
     * tcpDiscoverySpi.
     */
    public TcpDiscoverySpi tcpDiscoverySpi(TcpDiscoveryVmIpFinder tcpDiscoveryVmIpFinder) {
        TcpDiscoverySpi tcpDiscoverySpi = new TcpDiscoverySpi();
        tcpDiscoverySpi.setIpFinder(tcpDiscoveryVmIpFinder);
        return tcpDiscoverySpi;
    }

    /**
     * tcpDiscoveryVmIpFinder.
     */
    @Bean
    public TcpDiscoveryVmIpFinder tcpDiscoveryVmIpFinder() {
        TcpDiscoveryVmIpFinder tcpDiscoveryVmIpFinder = new TcpDiscoveryVmIpFinder();
        tcpDiscoveryVmIpFinder.setShared(false);
        tcpDiscoveryVmIpFinder.setAddresses(Arrays.asList(tcpDiscoveryAddress));
        return tcpDiscoveryVmIpFinder;
    }

    /**
     * dataStorageConfiguration.
     */
    @Bean
    public DataStorageConfiguration dataStorageConfiguration(DataRegionConfiguration dataRegionConfiguration) {
        DataStorageConfiguration dataStorageConfiguration = new DataStorageConfiguration();
        dataStorageConfiguration.setDefaultDataRegionConfiguration(dataRegionConfiguration);
        return dataStorageConfiguration;
    }

    /**
     * dataRegionConfiguration.
     */
    @Bean
    public DataRegionConfiguration dataRegionConfiguration() {
        DataRegionConfiguration dataRegionConfiguration = new DataRegionConfiguration();
        dataRegionConfiguration.setPersistenceEnabled(true);
        return dataRegionConfiguration;
    }

}
