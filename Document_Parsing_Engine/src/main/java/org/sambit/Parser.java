package org.sambit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.sambit.Main.PdfExecutor;

public class Parser
{
    private static final Logger logger =
            LoggerFactory.getLogger(Parser.class);
    Parser parser = new Parser();
    public  void Parser(){
        //No Para Meterized Constructor.
    }
    public  void startProcess(){


        logger.info("This Printing information is for Parser  class");

        PdfExecutor executor = new PdfExecutor();

        executor.start();

        logger.info("Started Pdf Executor....");

    }
}
