package com.tn.summarize.app.domain;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public record Folder(long id, @Nullable Long parentId, @NotNull String userId, @NotNull String name) {}
