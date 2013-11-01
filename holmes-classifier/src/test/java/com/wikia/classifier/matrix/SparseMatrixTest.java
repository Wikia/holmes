package com.wikia.classifier.matrix;


import com.google.common.collect.Sets;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.NoSuchElementException;

public class SparseMatrixTest {

    @Test
    public void testPutGet() {
        Matrix matrix = new SparseMatrix();
        matrix.put("A", "A", 1);
        matrix.put("B", "B", 2);

        Assert.assertEquals(matrix.get("A", "A"), 1.0d);
        Assert.assertEquals(matrix.get("B", "B"), 2.0d);
        Assert.assertEquals(matrix.get("A", "B"), 0.0d);
        Assert.assertEquals(matrix.get("B", "A"), 0.0d);
    }

    @Test( expectedExceptions = NoSuchElementException.class)
    public void testGetThrowsIfThereAreNoColumnOrRow() throws Exception {
        Matrix matrix = new SparseMatrix();
        double v = matrix.get("foo", "bar"); // throw !
        System.out.print(String.format("Result was: %f", v));
    }

    @Test
    public void testGetRow() throws Exception {
        Matrix matrix = new SparseMatrix();
        matrix.put("A", "A", 1);
        matrix.put("B", "B", 2);

        Assert.assertEquals(matrix.getRowVector("A").size(), 1 );
        Assert.assertEquals(matrix.getRowVector("A").get("B"), 0.0d);
        Assert.assertEquals(matrix.getRowVector("A").get("A"), 1.0d);
    }

    @Test
    public void testGetColVector() throws Exception {
        Matrix matrix = new SparseMatrix();
        matrix.put("A", "A", 1);
        matrix.put("B", "B", 2);

        Assert.assertEquals(matrix.getColumnVector("A").size(), 1);
        Assert.assertEquals(matrix.getColumnVector("A").get("B"), 0.0d);
        Assert.assertEquals(matrix.getColumnVector("A").get("A"), 1.0d);

    }

    @Test
    public void testGetRowNames() throws Exception {
        Matrix matrix = new SparseMatrix();
        matrix.put("RA", "A", 1);
        matrix.put("RB", "B", 2);
        matrix.put("RA", "B", 2);

        Assert.assertEquals(matrix.getRowNames(), Sets.newHashSet("RA", "RB"));
    }

    @Test
    public void testGetColumnNames() throws Exception {
        Matrix matrix = new SparseMatrix();
        matrix.put("RA", "A", 1);
        matrix.put("RB", "B", 2);
        matrix.put("RA", "B", 2);

        Assert.assertEquals(matrix.getRowNames(), Sets.newHashSet("RA", "RB"));
        Assert.assertEquals(matrix.getColumnNames(), Sets.newHashSet("A", "B"));
    }

    @Test
    public void testMerge() throws Exception {

    }
}
