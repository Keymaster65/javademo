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

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.util.Optional;

public class PersonTest {

    private static final String LAST_NAME = "lastName";
    private static final String FIRST_NAME = "firstName";

    @Property
    public void minBuilder(@ForAll final String lastName) {
        final Person.AddLastName builder = Person.minBuilder();

        final Person result = builder
                .withLastName(lastName);

        Assertions.assertEquals(result.lastName, lastName);
    }

    @Property
    public void withFirstNameBuilder(@ForAll final String firstName) {
        final Person.AddFirstName builder = Person.withFirstNameBuilder();

        final Person result = builder
                .withFirstName(Optional.of(firstName))
                .withLastName(LAST_NAME);

        Assertions.assertEquals(result.firstName, Optional.of(firstName));
    }


    @Property
    public void maxBuilder(@ForAll final LocalDate birthday) {
        final Person.AddBirthDay builder = Person.maxBuilder();

        final Person result = builder
                .withBirthDay(Optional.of(birthday))
                .withFirstName(Optional.of(FIRST_NAME))
                .withLastName(LAST_NAME);

        Assertions.assertEquals(result.birthDate, Optional.of(birthday));
    }
}