package com.tn.summarize.app.domain;

import lombok.NonNull;

public record UserAndTokens(@NonNull User user, @NonNull String refreshToken, @NonNull String accessToken) {}
