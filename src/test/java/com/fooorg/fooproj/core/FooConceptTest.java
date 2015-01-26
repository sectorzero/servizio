package com.fooorg.fooproj.core;

import java.util.concurrent.atomic.AtomicInteger;

import com.fooorg.fooproj.framework.FooProjUnitTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import org.mockito.InOrder;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.inOrder;
import static org.mockito.MockitoAnnotations.initMocks;

@Category(FooProjUnitTest.class)
public class FooConceptTest {

    @Mock
    FooDependencyA a;
    @Mock
    FooDependencyB b;
    @Mock
    AtomicInteger counter;

    FooConcept c;

    @Before
    public void setup() {
        initMocks(this);

        c = new FooConcept(a, b, counter);
    }

    @After
    public void teardown() {
    }

    @Test
    public void scenario_1() {
        // Arrange
        // already done, nothing special for this test

        // Act
        String output = c.doSomething();

        // Assert
        assertEquals(36, output.length());

        InOrder inorder = inOrder(a, b, a, counter);
        inorder.verify(a).invoke();
        inorder.verify(b).invoke();
        inorder.verify(a).invoke();
        inorder.verify(counter).incrementAndGet();
    }

}