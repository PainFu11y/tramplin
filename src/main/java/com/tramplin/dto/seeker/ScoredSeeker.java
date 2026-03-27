package com.tramplin.dto.seeker;

import com.tramplin.entity.Seeker;

public record ScoredSeeker(Seeker seeker, int score) {}