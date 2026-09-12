package org.sambit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Starter {

    private static final Logger logger =
            LoggerFactory.getLogger(Starter.class);

    public static void main(String[] args) {

        logger.info("Hello and welcome!");
        logger.info("Document Intelligence Engine is starting...");

        logger.info("Starting the Module ");
        try {

            for (int i = 1; i <= 5; i++) {

                logger.info(
                        "Our Engine  will start in {} seconds",
                        i
                );

                Thread.sleep(5000);
            }

            Parser parser = new Parser();
            parser.startProcess();
            logger.info("Project started successfully.");



        } catch (InterruptedException e) {

            logger.error(
                    "Something went wrong while testing",
                    e
            );

            Thread.currentThread().interrupt();
        }
    }
}