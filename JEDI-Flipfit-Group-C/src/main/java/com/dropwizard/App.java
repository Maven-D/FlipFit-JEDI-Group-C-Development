package com.dropwizard;

import com.flipfit.business.FlipFitBookingServiceImpl;
import com.flipfit.business.FlipFitBookingServiceInterface;
import com.flipfit.business.FlipFitGymServiceImpl;
import com.flipfit.business.FlipFitGymServiceInterface;
import com.flipfit.rest.CustomerController;
import com.flipfit.rest.FlipFitController;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.PathParam;

public class App extends Application<Configuration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    @Override
    public void initialize(Bootstrap<Configuration> b) {
    }

    @Override
    public void run(Configuration c, Environment e) throws Exception {
        LOGGER.info("Registering REST resources");

        // 1. Create an instance of your controller
        final FlipFitController controller = new FlipFitController();

        // 2. Register the controller instance with Dropwizard's environment
        e.jersey().register(controller);

        // --- Customer Controller Setup ---
        final FlipFitGymServiceInterface gymService = new FlipFitGymServiceImpl();
        final FlipFitBookingServiceInterface bookingService = new FlipFitBookingServiceImpl();
        final CustomerController customerController = new CustomerController(gymService, bookingService);
        e.jersey().register(customerController);

    }


    public static void main(String[] args) throws Exception {
        new App().run(args);
    }
}

