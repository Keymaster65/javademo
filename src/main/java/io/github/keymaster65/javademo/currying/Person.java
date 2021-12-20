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

import java.time.LocalDate;
import java.util.Optional;

public class Person {
    public final String lastName;

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public final Optional<LocalDate> birthDate;

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public final Optional<String> firstName;

    public static AddLastName minBuilder() {
        return Person::new;
    }

    public static AddBirthDay maxBuilder() {
        return birthdate
                -> firstName
                -> lastName
                -> new Person(lastName, firstName, birthdate);
    }

    static AddFirstName withFirstNameBuilder() {
        return firstName
                -> lastName
                -> new Person(lastName, firstName, Optional.empty());
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    Person(
            final String lastName,
            final Optional<String> firstName,
            final Optional<LocalDate> birthDate
    ) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
    }

    Person(
            final String lastName
    ) {
        this(lastName, Optional.empty(), Optional.empty());
    }


    public interface AddBirthDay {
        @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
        Person.AddFirstName withBirthDay(final Optional<LocalDate> birthDate);
    }

    public interface AddFirstName {
        @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
        Person.AddLastName withFirstName(final Optional<String> firstName);
    }

    public interface AddLastName {
        Person withLastName(final String lastName);
    }
}