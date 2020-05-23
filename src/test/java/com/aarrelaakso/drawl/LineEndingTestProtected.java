package com.aarrelaakso.drawl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.BDDAssertions.then;

@DisplayName("LineEnding - Protected API")
public class LineEndingTestProtected {

    @DisplayName("When a LineEnding is created, then the user can set the relative size")
    @ParameterizedTest
    @EnumSource(LineEnding.Type.class)
    void whenALineEndingIsCreatedThenTheUserCanSetTheSize(LineEnding.Type type) {
        final LineEnding lineEnding = LineEnding.newInstance(type);
        double EXPECTED = ThreadLocalRandom.current().nextDouble(10);
        lineEnding.setSize(EXPECTED);
        double ACTUAL = lineEnding.getHeight();
        then(ACTUAL).isEqualTo(EXPECTED);
    }

    @DisplayName("When a LineEnding is created, then it has a unique ID")
    @ParameterizedTest
    @EnumSource(LineEnding.Type.class)
    void whenALineEndingIsCreatedThenItHasAUniqueID(LineEnding.Type type) {
        HashSet<String> uniqueIds = new HashSet<String>();
        final LineEnding lineEnding = LineEnding.newInstance(type);
        String uniqueId = lineEnding.getUniqueId();
        then(uniqueId).isNotIn(uniqueIds);
        uniqueIds.add(uniqueId);
    }

}
