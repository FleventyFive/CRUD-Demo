package com.fleventyfive.CRUDDemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CrudDemoApplication {

	private static final Logger log = LoggerFactory.getLogger(CrudDemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CrudDemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(PlayerRepository repository) {
		return (args) -> {
 			repository.save(new Player("FleventyFive", 100));
			repository.save(new Player("LootLad", 76));
			repository.save(new Player("CritRateKing", 47));
			repository.save(new Player("JavaCoder21", 31));
			repository.save(new Player("PlzGiveLootAmNew", 11));


			log.info("All players in database:");
			for (Player player : repository.findAll())
				log.info(player.toString());
			log.info("");
		};
	}
}
