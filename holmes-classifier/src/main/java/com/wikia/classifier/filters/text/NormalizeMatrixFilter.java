package com.wikia.classifier.filters.text;

import com.wikia.classifier.filters.FilterBase;
import com.wikia.classifier.util.matrix.Matrix;

import java.util.Map;

public class NormalizeMatrixFilter extends FilterBase<Matrix, Matrix> {
    private static final long serialVersionUID = 1112236278930618221L;

    public NormalizeMatrixFilter() {
        super(Matrix.class, Matrix.class);
    }

    @Override
    protected Matrix doFilter(Matrix params) {
        for( String rowName: params.getRowNames() )
            for (Map.Entry<String, Double> colVal : params.getRowVector(rowName).getNonZeroValues().entrySet()) {
                colVal.setValue( colVal.getValue() > 0.0d ? 1.0d : 0.0d );
            }
        return params;
    }
}
