package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@Configuration
public class HazelcastCacheConfiguration {
	@Bean
	public HazelcastInstance hzcInstance() {

		final Config cfg = new Config();
		cfg.getNetworkConfig().setPort(6802);
		cfg.getNetworkConfig().setPortAutoIncrement(true);
		cfg.getGroupConfig().setName("hzcGroup");

		final NetworkConfig networkConfig = cfg.getNetworkConfig();
		final JoinConfig joinConfig = networkConfig.getJoin();
		joinConfig.getMulticastConfig().setEnabled(false);

		// Adding this instance to a cluster
		joinConfig.getTcpIpConfig().addMember("localhost").setEnabled(true);

		cfg.setProperty("hazelcast.jmx", "true");

		final HazelcastInstance hzcInstance = Hazelcast.newHazelcastInstance(cfg);

		return hzcInstance;

	}

}
