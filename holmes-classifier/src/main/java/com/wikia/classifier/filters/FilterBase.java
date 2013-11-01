package com.wikia.classifier.filters;
/**
 * Author: Artur Dwornik
 * Date: 07.04.13
 * Time: 15:17
 */

/**
 * Base class for filters. Handles type checking.
 * @see Filter
 * @param <TIn> argument type
 * @param <TOut> returned type
 */
public abstract class FilterBase<TIn, TOut> implements Filter<TIn, TOut> {
    private static final long serialVersionUID = -544455464982564434L;
    private final Class<? super TIn> inType;
    private final Class<? super TOut> outType;
    private boolean acceptingNullArguments = false;

    protected FilterBase(Class<? super TIn> inType, Class<? super TOut> outType) {
        this.inType = inType;
        this.outType = outType;
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public TOut filter(TIn argument) {
        if( !isAcceptingNullArguments() && argument == null ) {
            throw new IllegalArgumentException("Passed null argument to filter that does not support null arguments.");
        }
        if( argument != null && !inType.isInstance(argument) ) {
            throw new IllegalArgumentException("Argument has wrong type. Was "
                    + argument.getClass() + " expected " + inType);
        }
        TOut returnedValue = doFilter(argument);
        if( returnedValue != null && !outType.isInstance(returnedValue) ) {
            throw new IllegalStateException("Returned value had wrong type. Was"
                + returnedValue.getClass() + " expected" + outType);
        }
        return returnedValue;
    }

    protected abstract TOut doFilter(TIn params);

    public boolean isAcceptingNullArguments() {
        return acceptingNullArguments;
    }

    public void setAcceptingNullArguments(boolean acceptingNullArguments) {
        this.acceptingNullArguments = acceptingNullArguments;
    }
}
