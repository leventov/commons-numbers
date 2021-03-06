/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.numbers.quaternion;

import java.util.Random;

import org.junit.Test;
import org.junit.Assert;

public class QuaternionTest {
    /** Epsilon for double comparison. */
    private static final double EPS = Math.ulp(1d);
    /** Epsilon for double comparison. */
    private static final double COMPARISON_EPS = 1e-14;

    @Test
    public void testZeroQuaternion() {
        Assert.assertEquals(0, Quaternion.ZERO.norm(), 0d);
    }

    @Test
    public void testUnitQuaternions() {
        Assert.assertEquals(1, Quaternion.ONE.norm(), 0d);
        Assert.assertTrue(Quaternion.ONE.normalize() == Quaternion.ONE);

        Assert.assertEquals(1, Quaternion.I.norm(), 0d);
        Assert.assertTrue(Quaternion.I.normalize() == Quaternion.I);

        Assert.assertEquals(1, Quaternion.J.norm(), 0d);
        Assert.assertTrue(Quaternion.J.normalize() == Quaternion.J);

        Assert.assertEquals(1, Quaternion.K.norm(), 0d);
        Assert.assertTrue(Quaternion.K.normalize() == Quaternion.K);
    }

    @Test
    public final void testAccessors1() {
        final double q0 = 2;
        final double q1 = 5.4;
        final double q2 = 17;
        final double q3 = 0.0005;
        final Quaternion q = Quaternion.of(q0, q1, q2, q3);

        Assert.assertEquals(q0, q.getW(), 0);
        Assert.assertEquals(q1, q.getX(), 0);
        Assert.assertEquals(q2, q.getY(), 0);
        Assert.assertEquals(q3, q.getZ(), 0);
    }

    @Test
    public final void testAccessors2() {
        final double q0 = 2;
        final double q1 = 5.4;
        final double q2 = 17;
        final double q3 = 0.0005;
        final Quaternion q = Quaternion.of(q0, q1, q2, q3);

        final double sP = q.getScalarPart();
        final double[] vP = q.getVectorPart();

        Assert.assertEquals(q0, sP, 0);
        Assert.assertEquals(q1, vP[0], 0);
        Assert.assertEquals(q2, vP[1], 0);
        Assert.assertEquals(q3, vP[2], 0);
    }

    @Test
    public final void testAccessors3() {
        final double q0 = 2;
        final double q1 = 5.4;
        final double q2 = 17;
        final double q3 = 0.0005;
        final Quaternion q = Quaternion.of(q0, new double[] { q1, q2, q3 });

        final double sP = q.getScalarPart();
        final double[] vP = q.getVectorPart();

        Assert.assertEquals(q0, sP, 0);
        Assert.assertEquals(q1, vP[0], 0);
        Assert.assertEquals(q2, vP[1], 0);
        Assert.assertEquals(q3, vP[2], 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testWrongDimension() {
        Quaternion.of(new double[] { 1, 2 });
    }

    @Test
    public final void testConjugate() {
        final double q0 = 2;
        final double q1 = 5.4;
        final double q2 = 17;
        final double q3 = 0.0005;
        final Quaternion q = Quaternion.of(q0, q1, q2, q3);

        final Quaternion qConjugate = q.conjugate();

        Assert.assertEquals(q0, qConjugate.getW(), 0);
        Assert.assertEquals(-q1, qConjugate.getX(), 0);
        Assert.assertEquals(-q2, qConjugate.getY(), 0);
        Assert.assertEquals(-q3, qConjugate.getZ(), 0);
    }

    /* TODO remove dependency on Vector3D
    @Test
    public final void testProductQuaternionQuaternion() {

        // Case : analytic test case

        final Quaternion qA = Quaternion.of(1, 0.5, -3, 4);
        final Quaternion qB = Quaternion.of(6, 2, 1, -9);
        final Quaternion qResult = Quaternion.multiply(qA, qB);

        Assert.assertEquals(44, qResult.getW(), EPS);
        Assert.assertEquals(28, qResult.getX(), EPS);
        Assert.assertEquals(-4.5, qResult.getY(), EPS);
        Assert.assertEquals(21.5, qResult.getZ(), EPS);

        // comparison with the result given by the formula :
        // qResult = (scalarA * scalarB - vectorA . vectorB) + (scalarA * vectorB + scalarB * vectorA + vectorA ^
        // vectorB)

        final Vector3D vectorA = new Vector3D(qA.getVectorPart());
        final Vector3D vectorB = new Vector3D(qB.getVectorPart());
        final Vector3D vectorResult = new Vector3D(qResult.getVectorPart());

        final double scalarPartRef = qA.getScalarPart() * qB.getScalarPart() - Vector3D.dotProduct(vectorA, vectorB);

        Assert.assertEquals(scalarPartRef, qResult.getScalarPart(), EPS);

        final Vector3D vectorPartRef = ((vectorA.scalarMultiply(qB.getScalarPart())).add(vectorB.scalarMultiply(qA
                .getScalarPart()))).add(Vector3D.crossProduct(vectorA, vectorB));
        final double norm = (vectorResult.subtract(vectorPartRef)).norm();

        Assert.assertEquals(0, norm, EPS);

        // Conjugate of the product of two quaternions and product of their conjugates :
        // Conj(qA * qB) = Conj(qB) * Conj(qA)

        final Quaternion conjugateOfProduct = qB.getConjugate().multiply(qA.getConjugate());
        final Quaternion productOfConjugate = (qA.multiply(qB)).getConjugate();

        Assert.assertEquals(conjugateOfProduct.getW(), productOfConjugate.getW(), EPS);
        Assert.assertEquals(conjugateOfProduct.getX(), productOfConjugate.getX(), EPS);
        Assert.assertEquals(conjugateOfProduct.getY(), productOfConjugate.getY(), EPS);
        Assert.assertEquals(conjugateOfProduct.getZ(), productOfConjugate.getZ(), EPS);
    }
    */
    /* TODO remove dependency on Vector3D
    @Test
    public final void testProductQuaternionVector() {

        // Case : Product between a vector and a quaternion : QxV

        final Quaternion quaternion = Quaternion.of(4, 7, -1, 2);
        final double[] vector = {2.0, 1.0, 3.0};
        final Quaternion qResultQxV = Quaternion.multiply(quaternion, Quaternion.of(vector));

        Assert.assertEquals(-19, qResultQxV.getW(), EPS);
        Assert.assertEquals(3, qResultQxV.getX(), EPS);
        Assert.assertEquals(-13, qResultQxV.getY(), EPS);
        Assert.assertEquals(21, qResultQxV.getZ(), EPS);

        // comparison with the result given by the formula :
        // qResult = (- vectorQ . vector) + (scalarQ * vector + vectorQ ^ vector)

        final double[] vectorQ = quaternion.getVectorPart();
        final double[] vectorResultQxV = qResultQxV.getVectorPart();

        final double scalarPartRefQxV = -Vector3D.dotProduct(new Vector3D(vectorQ), new Vector3D(vector));
        Assert.assertEquals(scalarPartRefQxV, qResultQxV.getScalarPart(), EPS);

        final Vector3D vectorPartRefQxV = (new Vector3D(vector).scalarMultiply(quaternion.getScalarPart())).add(Vector3D
                .crossProduct(new Vector3D(vectorQ), new Vector3D(vector)));
        final double normQxV = (new Vector3D(vectorResultQxV).subtract(vectorPartRefQxV)).norm();
        Assert.assertEquals(0, normQxV, EPS);

        // Case : Product between a vector and a quaternion : VxQ

        final Quaternion qResultVxQ = Quaternion.multiply(Quaternion.of(vector), quaternion);

        Assert.assertEquals(-19, qResultVxQ.getW(), EPS);
        Assert.assertEquals(13, qResultVxQ.getX(), EPS);
        Assert.assertEquals(21, qResultVxQ.getY(), EPS);
        Assert.assertEquals(3, qResultVxQ.getZ(), EPS);

        final double[] vectorResultVxQ = qResultVxQ.getVectorPart();

        // comparison with the result given by the formula :
        // qResult = (- vector . vectorQ) + (scalarQ * vector + vector ^ vectorQ)

        final double scalarPartRefVxQ = -Vector3D.dotProduct(new Vector3D(vectorQ), new Vector3D(vector));
        Assert.assertEquals(scalarPartRefVxQ, qResultVxQ.getScalarPart(), EPS);

        final Vector3D vectorPartRefVxQ = (new Vector3D(vector).scalarMultiply(quaternion.getScalarPart())).add(Vector3D
                .crossProduct(new Vector3D(vector), new Vector3D(vectorQ)));
        final double normVxQ = (new Vector3D(vectorResultVxQ).subtract(vectorPartRefVxQ)).norm();
        Assert.assertEquals(0, normVxQ, EPS);
    }
    */
    @Test
    public final void testDotProductQuaternionQuaternion() {
        // expected output
        final double expected = -6.;
        // inputs
        final Quaternion q1 = Quaternion.of(1, 2, 2, 1);
        final Quaternion q2 = Quaternion.of(3, -2, -1, -3);

        final double actual1 = Quaternion.dotProduct(q1, q2);
        final double actual2 = q1.dotProduct(q2);

        Assert.assertEquals(expected, actual1, EPS);
        Assert.assertEquals(expected, actual2, EPS);
    }

    @Test
    public final void testScalarMultiplyDouble() {
        // expected outputs
        final double w = 1.6;
        final double x = -4.8;
        final double y = 11.20;
        final double z = 2.56;
        // inputs
        final Quaternion q1 = Quaternion.of(0.5, -1.5, 3.5, 0.8);
        final double a = 3.2;

        final Quaternion q = q1.multiply(a);

        Assert.assertEquals(w, q.getW(), COMPARISON_EPS);
        Assert.assertEquals(x, q.getX(), COMPARISON_EPS);
        Assert.assertEquals(y, q.getY(), COMPARISON_EPS);
        Assert.assertEquals(z, q.getZ(), COMPARISON_EPS);
    }

    @Test
    public final void testAddQuaternionQuaternion() {
        // expected outputs
        final double w = 4;
        final double x = -1;
        final double y = 2;
        final double z = -4;
        // inputs
        final Quaternion q1 = Quaternion.of(1., 2., -2., -1.);
        final Quaternion q2 = Quaternion.of(3., -3., 4., -3.);

        final Quaternion qa = Quaternion.add(q1, q2);
        final Quaternion qb = q1.add(q2);

        Assert.assertEquals(w, qa.getW(), EPS);
        Assert.assertEquals(x, qa.getX(), EPS);
        Assert.assertEquals(y, qa.getY(), EPS);
        Assert.assertEquals(z, qa.getZ(), EPS);

        Assert.assertEquals(w, qb.getW(), EPS);
        Assert.assertEquals(x, qb.getX(), EPS);
        Assert.assertEquals(y, qb.getY(), EPS);
        Assert.assertEquals(z, qb.getZ(), EPS);
    }

    @Test
    public final void testSubtractQuaternionQuaternion() {
        // expected outputs
        final double w = -2.;
        final double x = 5.;
        final double y = -6.;
        final double z = 2.;
        // inputs
        final Quaternion q1 = Quaternion.of(1., 2., -2., -1.);
        final Quaternion q2 = Quaternion.of(3., -3., 4., -3.);

        final Quaternion qa = Quaternion.subtract(q1, q2);
        final Quaternion qb = q1.subtract(q2);

        Assert.assertEquals(w, qa.getW(), EPS);
        Assert.assertEquals(x, qa.getX(), EPS);
        Assert.assertEquals(y, qa.getY(), EPS);
        Assert.assertEquals(z, qa.getZ(), EPS);

        Assert.assertEquals(w, qb.getW(), EPS);
        Assert.assertEquals(x, qb.getX(), EPS);
        Assert.assertEquals(y, qb.getY(), EPS);
        Assert.assertEquals(z, qb.getZ(), EPS);
}

    @Test
    public final void testNorm() {

        final double q0 = 2;
        final double q1 = 1;
        final double q2 = -4;
        final double q3 = 3;
        final Quaternion q = Quaternion.of(q0, q1, q2, q3);

        final double norm = q.norm();

        Assert.assertEquals(Math.sqrt(30), norm, 0);

        final double normSquareRef = Quaternion.multiply(q, q.conjugate()).getScalarPart();
        Assert.assertEquals(Math.sqrt(normSquareRef), norm, 0);
    }

    @Test
    public final void testNormalize() {

        final Quaternion q = Quaternion.of(2, 1, -4, -2);

        final Quaternion versor = q.normalize();

        Assert.assertEquals(2.0 / 5.0, versor.getW(), 0);
        Assert.assertEquals(1.0 / 5.0, versor.getX(), 0);
        Assert.assertEquals(-4.0 / 5.0, versor.getY(), 0);
        Assert.assertEquals(-2.0 / 5.0, versor.getZ(), 0);

        Assert.assertEquals(1, versor.norm(), 0);

        Assert.assertTrue(versor.normalize() == versor);
    }

    @Test(expected=IllegalStateException.class)
    public final void testNormalizeFail_zero() {
        final Quaternion q = Quaternion.of(0, 0, 0, 0);
        q.normalize();
    }

    @Test(expected=IllegalStateException.class)
    public final void testNormalizeFail_nan() {
        final Quaternion q = Quaternion.of(0, 0, 0, Double.NaN);
        q.normalize();
    }

    @Test(expected=IllegalStateException.class)
    public final void testNormalizeFail_positiveInfinity() {
        final Quaternion q = Quaternion.of(0, 0, Double.POSITIVE_INFINITY, 0);
        q.normalize();
    }

    @Test(expected=IllegalStateException.class)
    public final void testNormalizeFail_negativeInfinity() {
        final Quaternion q = Quaternion.of(0, Double.NEGATIVE_INFINITY, 0, 0);
        q.normalize();
    }

    @Test
    public final void testObjectEquals() {
        final double one = 1;
        final Quaternion q1 = Quaternion.of(one, one, one, one);
        Assert.assertTrue(q1.equals(q1));

        final Quaternion q2 = Quaternion.of(one, one, one, one);
        Assert.assertTrue(q2.equals(q1));

        final Quaternion q3 = Quaternion.of(one, Math.nextUp(one), one, one);
        Assert.assertFalse(q3.equals(q1));

        Assert.assertFalse(q3.equals("bar"));
    }

    @Test
    public void testHashCode() {
        Quaternion x = Quaternion.of(0.0, 0.0, 0.0, 0.0);
        Quaternion y = Quaternion.of(0.0, 0.0 + Double.MIN_VALUE, 0.0, 0.0);
        Assert.assertFalse(x.hashCode()==y.hashCode());
        y = Quaternion.of(0.0 + Double.MIN_VALUE, 0.0, 0.0, 0.0);
        Assert.assertFalse(x.hashCode()==y.hashCode());

        // "equals" and "hashCode" must be compatible: if two objects have
        // different hash codes, "equals" must return false.
        final String msg = "'equals' not compatible with 'hashCode'";

        x = Quaternion.of(0.0, 0.0, 0.0, 0.0);
        y = Quaternion.of(-0.0, 0.0, 0.0, 0.0);
        Assert.assertTrue(x.hashCode() != y.hashCode());
        Assert.assertFalse(msg, x.equals(y));

        x = Quaternion.of(0.0, 0.0, 0.0, 0.0);
        y = Quaternion.of(0.0, -0.0, 0.0, 0.0);
        Assert.assertTrue(x.hashCode() != y.hashCode());
        Assert.assertFalse(msg, x.equals(y));

        x = Quaternion.of(0.0, 0.0, 0.0, 0.0);
        y = Quaternion.of(0.0, 0.0, -0.0, 0.0);
        Assert.assertTrue(x.hashCode() != y.hashCode());
        Assert.assertFalse(msg, x.equals(y));

        x = Quaternion.of(0.0, 0.0, 0.0, 0.0);
        y = Quaternion.of(0.0, 0.0, 0.0, -0.0);
        Assert.assertTrue(x.hashCode() != y.hashCode());
        Assert.assertFalse(msg, x.equals(y));
    }

    @Test
    public final void testQuaternionEquals() {
        final double inc = 1e-5;
        final Quaternion q1 = Quaternion.of(2, 1, -4, -2);
        final Quaternion q2 = Quaternion.of(q1.getW() + inc, q1.getX(), q1.getY(), q1.getZ());
        final Quaternion q3 = Quaternion.of(q1.getW(), q1.getX() + inc, q1.getY(), q1.getZ());
        final Quaternion q4 = Quaternion.of(q1.getW(), q1.getX(), q1.getY() + inc, q1.getZ());
        final Quaternion q5 = Quaternion.of(q1.getW(), q1.getX(), q1.getY(), q1.getZ() + inc);

        Assert.assertFalse(q1.equals(q2, 0.9 * inc));
        Assert.assertFalse(q1.equals(q3, 0.9 * inc));
        Assert.assertFalse(q1.equals(q4, 0.9 * inc));
        Assert.assertFalse(q1.equals(q5, 0.9 * inc));

        Assert.assertTrue(q1.equals(q2, 1.1 * inc));
        Assert.assertTrue(q1.equals(q3, 1.1 * inc));
        Assert.assertTrue(q1.equals(q4, 1.1 * inc));
        Assert.assertTrue(q1.equals(q5, 1.1 * inc));
    }

    @Test
    public final void testQuaternionEquals2() {
        final Quaternion q1 = Quaternion.of(1, 4, 2, 3);
        final double gap = 1e-5;
        final Quaternion q2 = Quaternion.of(1 + gap, 4 + gap, 2 + gap, 3 + gap);

        Assert.assertTrue(q1.equals(q2, 10 * gap));
        Assert.assertFalse(q1.equals(q2, gap));
        Assert.assertFalse(q1.equals(q2, gap / 10));
    }

    @Test
    public final void testIsUnit() {
        final Random r = new Random(48);
        final int numberOfTrials = 1000;
        for (int i = 0; i < numberOfTrials; i++) {
            final Quaternion q1 = Quaternion.of(r.nextDouble(), r.nextDouble(), r.nextDouble(), r.nextDouble());
            final Quaternion q2 = q1.normalize();
            Assert.assertTrue(q2.isUnit(COMPARISON_EPS));
        }

        final Quaternion q = Quaternion.of(1, 1, 1, 1);
        Assert.assertFalse(q.isUnit(COMPARISON_EPS));
    }

    @Test
    public final void testIsPure() {
        final Quaternion q1 = Quaternion.of(0, 5, 4, 8);
        Assert.assertTrue(q1.isPure(EPS));

        final Quaternion q2 = Quaternion.of(0 - EPS, 5, 4, 8);
        Assert.assertTrue(q2.isPure(EPS));

        final Quaternion q3 = Quaternion.of(0 - 1.1 * EPS, 5, 4, 8);
        Assert.assertFalse(q3.isPure(EPS));

        final Random r = new Random(48);
        final double[] v = {r.nextDouble(), r.nextDouble(), r.nextDouble()};
        final Quaternion q4 = Quaternion.of(v);
        Assert.assertTrue(q4.isPure(0));

        final Quaternion q5 = Quaternion.of(0, v);
        Assert.assertTrue(q5.isPure(0));
    }

    @Test
    public final void testPositivePolarFormWhenScalarPositive() {
        Quaternion q = Quaternion.of(3, -3, -3, 3).positivePolarForm();
        Quaternion expected = Quaternion.of(0.5, -0.5, -0.5, 0.5);
        assertEquals(q, expected, EPS);

        Assert.assertTrue(q.positivePolarForm() == q);
    }

    @Test
    public final void testPositivePolarFormWhenScalarNegative() {
        Quaternion q = Quaternion.of(-3, 3, -3, 3).positivePolarForm();
        Quaternion expected = Quaternion.of(0.5, -0.5, 0.5, -0.5);
        assertEquals(q, expected, EPS);

        Assert.assertTrue(q.positivePolarForm() == q);
    }

    @Test
    public final void testPositivePolarFormWhenScalarPositiveAndNormalized() {
        Quaternion q = Quaternion.of(123, 45, 67, 89).normalize().positivePolarForm();

        Assert.assertTrue(q.getW() >= 0);
        Assert.assertTrue(q.positivePolarForm() == q);
    }

    @Test
    public final void testPositivePolarFormWhenScalarNegativeAndNormalized() {
        Quaternion q = Quaternion.of(123, 45, 67, 89).normalize().negate().positivePolarForm();

        Assert.assertTrue(q.getW() >= 0);
        Assert.assertTrue(q.positivePolarForm() == q);
    }

    @Test
    public void testNegate() {
        final double a = -1;
        final double b = 2;
        final double c = -3;
        final double d = 4;
        final Quaternion q = Quaternion.of(a, b, c, d);
        final Quaternion qNeg = q.negate();
        Assert.assertEquals(-a, qNeg.getW(), 0d);
        Assert.assertEquals(-b, qNeg.getX(), 0d);
        Assert.assertEquals(-c, qNeg.getY(), 0d);
        Assert.assertEquals(-d, qNeg.getZ(), 0d);

        Assert.assertTrue(q.equals(qNeg.negate(), 0d));
    }

    @Test
    public void testNegateNormalized() {
        final double a = -1;
        final double b = 2;
        final double c = -3;
        final double d = 4;
        final Quaternion q = Quaternion.of(a, b, c, d).normalize();
        final Quaternion qNeg = q.negate();
        Assert.assertTrue(q.equals(qNeg.negate(), 0d));
    }

    @Test
    public void testNegatePositivePolarForm() {
        final double a = -1;
        final double b = 2;
        final double c = -3;
        final double d = 4;
        final Quaternion q = Quaternion.of(a, b, c, d).positivePolarForm();
        final Quaternion qNeg = q.negate();
        Assert.assertTrue(q.equals(qNeg.negate(), 0d));
    }

    /* TODO remove dependency on Rotation
    @Test
    public final void testPolarForm() {
        final Random r = new Random(48);
        final int numberOfTrials = 1000;
        for (int i = 0; i < numberOfTrials; i++) {
            final Quaternion q = Quaternion.of(2 * (r.nextDouble() - 0.5), 2 * (r.nextDouble() - 0.5),
                                                2 * (r.nextDouble() - 0.5), 2 * (r.nextDouble() - 0.5));
            final Quaternion qP = q.positivePolarForm();

            Assert.assertTrue(qP.isUnit(COMPARISON_EPS));
            Assert.assertTrue(qP.getW() >= 0);

            final Rotation rot = new Rotation(q.getW(), q.getX(), q.getY(), q.getZ(), true);
            final Rotation rotP = new Rotation(qP.getW(), qP.getX(), qP.getY(), qP.getZ(), true);

            Assert.assertEquals(rot.getAngle(), rotP.getAngle(), COMPARISON_EPS);
            Assert.assertEquals(rot.getAxis(RotationConvention.VECTOR_OPERATOR).getX(),
                                rot.getAxis(RotationConvention.VECTOR_OPERATOR).getX(),
                                COMPARISON_EPS);
            Assert.assertEquals(rot.getAxis(RotationConvention.VECTOR_OPERATOR).getY(),
                                rot.getAxis(RotationConvention.VECTOR_OPERATOR).getY(),
                                COMPARISON_EPS);
            Assert.assertEquals(rot.getAxis(RotationConvention.VECTOR_OPERATOR).getZ(),
                                rot.getAxis(RotationConvention.VECTOR_OPERATOR).getZ(),
                                COMPARISON_EPS);
        }
    }
*/
    @Test
    public final void testInverse() {
        final Quaternion q = Quaternion.of(1.5, 4, 2, -2.5);

        final Quaternion inverseQ = q.inverse();
        Assert.assertEquals(1.5 / 28.5, inverseQ.getW(), 0);
        Assert.assertEquals(-4.0 / 28.5, inverseQ.getX(), 0);
        Assert.assertEquals(-2.0 / 28.5, inverseQ.getY(), 0);
        Assert.assertEquals(2.5 / 28.5, inverseQ.getZ(), 0);

        final Quaternion product = Quaternion.multiply(inverseQ, q);
        Assert.assertEquals(1, product.getW(), EPS);
        Assert.assertEquals(0, product.getX(), EPS);
        Assert.assertEquals(0, product.getY(), EPS);
        Assert.assertEquals(0, product.getZ(), EPS);

        final Quaternion qNul = Quaternion.of(0, 0, 0, 0);
        try {
            final Quaternion inverseQNul = qNul.inverse();
            Assert.fail("expecting ZeroException but got : " + inverseQNul);
        } catch (IllegalStateException ex) {
            // expected
        }
    }

    @Test(expected=IllegalStateException.class)
    public void testInverse_zeroNorm() {
        Quaternion q = Quaternion.of(0, 0, 0, 0);
        q.inverse();
    }

    @Test(expected=IllegalStateException.class)
    public void testInverse_nanNorm() {
        Quaternion q = Quaternion.of(Double.NaN, 0, 0, 0);
        q.inverse();
    }

    @Test(expected=IllegalStateException.class)
    public void testInverse_positiveInfinityNorm() {
        Quaternion q = Quaternion.of(0, Double.POSITIVE_INFINITY, 0, 0);
        q.inverse();
    }

    @Test(expected=IllegalStateException.class)
    public void testInverse_negativeInfinityNorm() {
        Quaternion q = Quaternion.of(0, 0, Double.NEGATIVE_INFINITY, 0);
        q.inverse();
    }

    @Test
    public void testInverseNormalized() {
        final Quaternion invQ = Quaternion.of(-1.2, 3.4, -5.6, -7.8).normalize().inverse();
        final Quaternion q = invQ.inverse();
        final Quaternion result = q.multiply(invQ);
        Assert.assertTrue(result.toString(), Quaternion.ONE.equals(result, EPS));
    }

    @Test
    public void testInversePositivePolarForm() {
        final Quaternion invQ = Quaternion.of(1.2, -3.4, 5.6, -7.8).positivePolarForm().inverse();
        final Quaternion q = invQ.inverse();
        final Quaternion result = q.multiply(invQ);
        Assert.assertTrue(result.toString(), Quaternion.ONE.equals(result, EPS));
    }

    @Test
    public final void testMultiply() {
        final Quaternion q1 = Quaternion.of(1, 2, 3, 4);
        final Quaternion q2 = Quaternion.of(4, 3, 2, 1);
        final Quaternion actual = q1.multiply(q2);
        final double w = 1 * 4 - 2 * 3 - 3 * 2 - 4 * 1;
        final double x = 1 * 3 + 2 * 4 + 3 * 1 - 4 * 2;
        final double y = 1 * 2 - 2 * 1 + 3 * 4 + 4 * 3;
        final double z = 1 * 1 + 2 * 2 - 3 * 3 + 4 * 4;
        final Quaternion expected = Quaternion.of(w, x, y, z);
        assertEquals(actual, expected, EPS);
    }

    @Test
    public final void testParseFromToString() {
        final Quaternion q = Quaternion.of(1.1, 2.2, 3.3, 4.4);
        Quaternion parsed = Quaternion.parse(q.toString());
        assertEquals(parsed, q, EPS);
    }

    @Test
    public final void testParseSpecials() {
        Quaternion parsed = Quaternion.parse("[1e-5 Infinity NaN -0xa.cp0]");
        Assert.assertEquals(1e-5, parsed.getW(), EPS);
        Assert.assertTrue(Double.isInfinite(parsed.getX()));
        Assert.assertTrue(Double.isNaN(parsed.getY()));
        Assert.assertEquals(-0xa.cp0, parsed.getZ(), EPS);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testParseMissingStart() {
        Quaternion.parse("1.0 2.0 3.0 4.0]");
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testParseMissingEnd() {
        Quaternion.parse("[1.0 2.0 3.0 4.0");
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testParseMissingPart() {
        Quaternion.parse("[1.0 2.0 3.0 ]");
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testParseInvalidScalar() {
        Quaternion.parse("[1.x 2.0 3.0 4.0]");
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testParseInvalidI() {
        Quaternion.parse("[1.0 2.0x 3.0 4.0]");
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testParseInvalidJ() {
        Quaternion.parse("[1.0 2.0 3.0x 4.0]");
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testParseInvalidK() {
        Quaternion.parse("[1.0 2.0 3.0 4.0x]");
    }

    @Test
    public final void testToString() {
        final Quaternion q = Quaternion.of(1, 2, 3, 4);
        Assert.assertEquals("[1.0 2.0 3.0 4.0]", q.toString());
    }

    /**
     * Assert that two quaternions are equal within tolerance
     * @param actual
     * @param expected
     * @param tolerance
     */
    private void assertEquals(Quaternion actual, Quaternion expected, double tolerance) {
        Assert.assertTrue("expecting " + expected + " but got " + actual, actual.equals(expected, tolerance));
    }

}
