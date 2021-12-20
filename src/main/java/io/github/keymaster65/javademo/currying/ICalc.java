/*
 * Copyright 2021 Wolf Sluyterman van Langeweyde
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.keymaster65.javademo.currying;

public interface ICalc {

    default AddFactor2 mult() {
        return factor1 -> factor2 -> factor1 * factor2;
    }

    default AddFactor1 mult3() {
        return factor0 -> factor1 -> factor2 ->
                mult()
                        .withFactor(factor0)
                        .withFactor(
                                mult()
                                        .withFactor(factor1)
                                        .withFactor(factor2));
    }

    interface AddFactor1 {
        AddFactor2 withFactor(final int factor1);
    }

    interface AddFactor2 {
        AddFactor3 withFactor(final int factor2);
    }

    interface AddFactor3 {
        int withFactor(final int factor3);
    }
}
