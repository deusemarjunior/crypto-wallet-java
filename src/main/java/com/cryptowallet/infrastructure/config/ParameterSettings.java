package com.cryptowallet.infrastructure.config;

import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;

public final class ParameterSettings {

    public static final int MAX_CONCURRENT_REQUESTS = 3;
    public static final int TIMEOUT_TASK_IN_SECONDS = 10;
    public static final int SCALE = 2;
    public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    public static final String API_URL = "https://api.coincap.io/v2";
    public static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private ParameterSettings() {
    }

}
