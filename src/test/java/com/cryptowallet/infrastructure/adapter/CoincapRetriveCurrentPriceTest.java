package com.cryptowallet.infrastructure.adapter;

import com.cryptowallet.infrastructure.adapter.CoincapRetriveCurrentPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class CoincapRetriveCurrentPriceTest {

    private CoincapRetriveCurrentPrice coincapRetriveCurrentPrice = new CoincapRetriveCurrentPrice();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCurrentCryptoPrice() throws Exception {
        // Given
        String id = "bitcoin";

        // When
        BigDecimal result = coincapRetriveCurrentPrice.getCurrentCryptoPrice(id);

        // Then
        assertEquals(new BigDecimal("56999.9728252053067291"), result);
    }

    @Test
    public void testGetCurrentCryptoPriceWithEmptyPrice() {
        // Given
        String id = "crypt-not-found-price";
        String responseBody = "{\"data\":[{}]}";
        try {
            // When
            coincapRetriveCurrentPrice.getCurrentCryptoPrice(id);
        } catch (Exception e) {
            // Then
            assertEquals(String.format("Error retrieving Current Price by id %s", id), e.getMessage());
        }
    }
}