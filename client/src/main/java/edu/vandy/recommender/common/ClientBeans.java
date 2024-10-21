package edu.vandy.recommender.common;

import edu.vandy.recommender.client.proxies.DatabaseAPI;
import edu.vandy.recommender.client.proxies.ParallelFluxAPI;
import edu.vandy.recommender.client.proxies.TimerAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.util.List;
import java.util.Map;

import static edu.vandy.recommender.common.Constants.GATEWAY_BASE_URL;

//import static edu.vandy.recommender.common.Constants.GATEWAY_BASE_URL;

/**
 * This class contains a {@code Bean} annotation that can be injected
 * into classes using the Spring {@code @Autowired} annotation.
 */
@Component
public class ClientBeans {
    /**
     * This factory method returns a new {@link RestTemplate}, which
     * enables a client to perform HTTP requests synchronously.
     *
     * @return A new {@link RestTemplate}
     */
    @Bean
    public RestTemplate getRestTemplate() {
        // Make a new RestTemplate.
        RestTemplate restTemplate = new RestTemplate();

        // Set the base URL for the RestTemplate to use
        // the API Gateway URL.

        restTemplate
            .setUriTemplateHandler(new DefaultUriBuilderFactory(GATEWAY_BASE_URL));

        // Return restTemplate.
        return restTemplate;
    }

    /**
     * @return A new instance of the {@link ParallelFluxAPI}
     */
    @Bean
    public ParallelFluxAPI getParallelFluxAPI() {
        // TODO -- you fill in here, replacing 'return null' with the
        // proper code.
        // Build and configure a WebClient instance with the base URL for the gateway
        return HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(WebClient
                        .builder()
                        // Set the base URL for all requests made through this client
                        .baseUrl(GATEWAY_BASE_URL)
                        // Build the WebClient instance
                        .build()))
                // Build the HttpServiceProxyFactory with the configured WebClient
                .build()
                // Create a client proxy for the ParallelFluxAPI interface
                .createClient(ParallelFluxAPI.class);
    }

    /**
     * Create an instance of the {@link DatabaseAPI} Retrofit client
     * that the {@link GsonConverterFactory} and make HTTP requests to
     * the {@code GatewayApplication} RESTful microservice.
     *
     * @return A {@link DatabaseAPI} instance
     */
    @Bean
    public DatabaseAPI getDatabaseAPI() {
        // TODO -- you fill in here, replacing 'return null' with the
        // proper code.
        // TODO -- you fill in here, replacing 'return null' with the
        // proper code.
        Retrofit retrofit = new Retrofit.Builder()
                // Set the base URL for the API
                .baseUrl(Constants.GATEWAY_BASE_URL)
                // Add GsonConverterFactory to parse JSON responses
                .addConverterFactory(GsonConverterFactory.create())
                // Build the Retrofit instance
                .build();

        return retrofit.create(DatabaseAPI.class);
    }

    /**
     * Create an instance of the {@link TimerAPI} Retrofit client that
     * uses {@link ScalarsConverterFactory} and makes HTTP requests to
     * the {@code GatewayApplication} RESTful microservice.
     *
     * @return A {@link TimerAPI} instance
     */
    @Bean
    public TimerAPI getTimerAPI() {
        // TODO -- you fill in here, replacing 'return null' with the
        // proper code.
        Retrofit retrofit = new Retrofit.Builder()
                // Set the base URL for the API
                .baseUrl(Constants.GATEWAY_BASE_URL)
                // Add ScalarsConverterFactory to handle String responses
                .addConverterFactory(ScalarsConverterFactory.create())
                // Build the Retrofit instance
                .build();

        // Create and return the TimerAPI instance
        return retrofit.create(TimerAPI.class);
    }

    /**
     * Loads the {@code dataset} and returns a {@link Map} of {@link
     * String} and {@link List<Double>} objects.
     *
     * @param dataset The name of the dataset containing movie-related
     *                data
     * @return A {@link Map} of {@link String} and {@link
     *         List<Double>} objects
     */
    @Bean
    static public Map<String, List<Double>> movieMap
    // The @Value annotation injects values into fields in
    // Spring-managed beans.
    (@Value("${app.dataset}") final String dataset) {
        return MovieDatasetReader
            .loadMovieData(dataset);
    }
}
