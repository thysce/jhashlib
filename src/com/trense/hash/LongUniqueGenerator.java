package com.trense.hash;

import javax.validation.constraints.NotNull;

/**
 * Generator for unique random long values
 *
 * @author Tim Trense
 */
public class LongUniqueGenerator extends RandomUniqueGenerator<Long> {

    public LongUniqueGenerator(@NotNull CheckUniqueFunction<Long> uniqueFunction) {
        super(uniqueFunction);
    }

    @Override
    protected @NotNull Long createRandom() {
        return getRandom().nextLong();
    }

}
