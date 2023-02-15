package by.parchevskaya;

import by.parchevskaya.model.Animal;
import by.parchevskaya.model.Car;
import by.parchevskaya.model.Flower;
import by.parchevskaya.model.House;
import by.parchevskaya.model.Person;
import by.parchevskaya.util.Util;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.stream.Collectors.*;

public class Main {
    public static void main(String[] args) throws IOException {
        task1();
        task2();
        task3();
        task4();
        task5();
        task6();
        task7();
        task8();
        task9();
        task10();
        task11();
        task12();
        task13();
        task14();
        task15();
    }

    private static void task1() throws IOException {
        int numInEachZoo = 7;
        List<Animal> animals = Util.getAnimals();
        animals.stream().filter(animal -> animal.getAge() >= 10 && animal.getAge() <20)
                .sorted(Comparator.comparingInt(Animal::getAge))
                .skip(numInEachZoo*2).limit(numInEachZoo)
                .forEach(System.out::println);
    }

    private static void task2() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> "Japanese".equals(animal.getOrigin()))
                //.peek(animal -> animal.setBread(animal.getBread().toUpperCase()))
                .map(animal -> {
                    animal.setBread(animal.getBread().toUpperCase());
                    return animal;
                })
                .filter(animal -> "Female".equals(animal.getGender()))
                .map(Animal::getBread)
                .forEach(System.out::println);
    }

    private static void task3() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getAge()>30)
                .map(Animal::getOrigin)
                .filter(country -> country.startsWith("A"))
                .distinct()
                .forEach(System.out::println);
    }

    private static void task4() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream().filter(animal -> "Female".equals(animal.getGender())).count());
    }

    private static void task5() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .filter(animal -> animal.getAge() >= 20 && animal.getAge() <= 30)
                .anyMatch(animal -> "Hungarian".equals(animal.getOrigin())));
    }

    private static void task6() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .allMatch(animal -> "Male".equals(animal.getGender()) || "Female".equals(animal.getGender())));
    }

    private static void task7() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream().noneMatch(animal -> "Oceania".equals(animal.getOrigin())));
    }

    private static void task8() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream().sorted(Comparator.comparing(Animal::getBread))
                .limit(100)
                .max(Comparator.comparingInt(Animal::getAge)).ifPresent(animal -> System.out.println(animal.getAge()));
    }

    private static void task9() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .map(Animal::getBread)
                .map(String::toCharArray)
                .min(Comparator.comparingInt(bread -> bread.length))
                .ifPresent(bread ->System.out.println(bread.length));
    }

    private static void task10() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream().mapToInt(Animal::getAge).sum());
    }

    private static void task11() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream().filter(animal -> "Indonesian".equals(animal.getOrigin()))
                .mapToInt(Animal::getAge).average().ifPresent(System.out::println);
    }

    private static void task12() throws IOException {
        Predicate<Person> agePredicate = person ->
                person.getDateOfBirth().isBefore(LocalDate.now().minusYears(18).plusDays(1))
                && person.getDateOfBirth().isAfter(LocalDate.now().minusYears(27).minusDays(1));
        List<Person> people = Util.getPersons();
        //Во Французский легион принимают людей со всего света,
        // но есть отбор по полу (только мужчины)
        people.stream().filter(person -> "Male".equals(person.getGender()))
        //возраст от 18 до 27 лет.
        .filter(agePredicate)
        // Преимущество отдаётся людям военной категории 1, на втором месте - военная категория 2, и на третьем месте военная категория 3.
        // Отсортировать всех подходящих кандидатов в порядке их приоритета по военной категории.
        .sorted(Comparator.comparingInt(Person::getRecruitmentGroup))
        // Однако взять на обучение академия может только 200 человек. Вывести их в консоль.
        .limit(200).forEach(System.out::println);
    }


    private static void task13() throws IOException {
        List<House> houses = Util.getHouses();
        List<Person> stage1 = houses.stream()
                .filter(house -> "Hospital".equals(house.getBuildingType()))
                .flatMap(house -> house.getPersonList().stream()).toList();
        List<Person> stage2 = houses.stream()
                .filter(house -> !"Hospital".equals(house.getBuildingType()))
                .flatMap(house -> house.getPersonList().stream())
                .filter(person -> person.getDateOfBirth().isAfter(LocalDate.now().minusYears(18))
                                || "Female".equals(person.getGender())
                                && person.getDateOfBirth().isBefore(LocalDate.now().minusYears(58).plusDays(1))
                                || "Male".equals(person.getGender())
                                && person.getDateOfBirth().isBefore(LocalDate.now().minusYears(63).plusDays(1)))
                .toList();
        List<Person> stage3 = houses.stream().flatMap(house -> house.getPersonList().stream()).toList();
        Stream.of(stage1, stage2, stage3).flatMap(Collection::stream)
                .distinct().limit(500).forEach(System.out::println);
    }

    private static void task14() throws IOException {
        //Iз перечня автомобилей приходящих на рынок Азии логистическому агентству предстоит отсортировать их в порядке следования
        //1.Туркменистан - 2.Узбекистан - 3.Казахстан - 4.Кыргызстан - 5.Россия - 6.Монголия.
        List<Car> cars = Util.getCars();
        BigDecimal perKg = BigDecimal.valueOf(7.14)
                .divide(BigDecimal.valueOf(1000), RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
        //Все автомобили марки "Jaguar" а так же все авто цвета White идут в первую страну.
        Predicate<Car> cars1Predicate = car -> "Jaguar".equals(car.getCarMake()) || "White".equals(car.getColor());
        //Iз оставшихся все автомобили с массой до 1500 кг и марок BMW, Lexus, Chrysler и Toyota идут во второй эшелон.
        List<String> carMakeFor2Echelon = List.of("BMW", "Lexus", "Chrysler", "Toyota");
        Predicate<Car> cars2Predicate = car -> car.getMass() < 1500 && carMakeFor2Echelon.contains(car.getCarMake());
        //Iз оставшихся все автомобили Черного цвета с массой более 4000 кг и все GMC и Dodge идут в третий эшелон.
        List<String> carMakeFor3ndEchelon = List.of("GMC", "Dodge");
        Predicate<Car> cars3Predicate = car ->
                "Black".equals(car.getColor()) && car.getMass() > 4000
                || carMakeFor3ndEchelon.contains(car.getCarMake());
        //Iз оставшихся все автомобили года выпуска до 1982 или все модели "Civic" и "Cherokee" идут в четвёртый эшелон.
        List<String> carMakeFor4Echelon = List.of("Civic", "Cherokee");
        Predicate<Car> cars4Predicate = car ->
                car.getReleaseYear() < 1982
                || carMakeFor4Echelon.contains(car.getCarModel());
        //Iз оставшихся все автомобили цветов НЕ Yellow, Red, Green и Blue или же по стоимости дороже 40000 в пятый эшелон
        List<String> carColorFor5Echelon = List.of("Yellow", "Red", "Green", "Blue");
        Predicate<Car> cars5Predicate = car ->
                !carColorFor5Echelon.contains(car.getColor())
                        || car.getPrice()>40000;
        //Iз оставшиеся все автомобили в vin номере которых есть цифра "59" идут в последний шестой эшелон.
        Predicate<Car> cars6Predicate = car -> car.getVin().contains("59");
        //Оставшиеся автомобили отбрасываем, они никуда не идут.

        var cars1 = cars.stream()
                .collect(partitioningBy(cars1Predicate, mapping(car->car, toList())));
        var cars2 = cars1.get(false).stream()
                .collect(partitioningBy(cars2Predicate, mapping(car->car, toList())));
        var cars3 = cars2.get(false).stream()
                .collect(partitioningBy(cars3Predicate, mapping(car->car, toList())));
        var cars4 = cars3.get(false).stream()
                .collect(partitioningBy(cars4Predicate, mapping(car->car, toList())));
        var cars5 = cars4.get(false).stream()
                .collect(partitioningBy(cars5Predicate, mapping(car->car, toList())));
        var cars6 = cars5.get(false).stream()
                .collect(partitioningBy(cars6Predicate, mapping(car->car, toList())));

        //Iзмерить суммарные массы автомобилей всех эшелонов для каждой из стран и подсчитать сколько для каждой страны
        //будет стоить транспортные расходы если учесть что на 1 тонну транспорта будет потрачено 7.14 $.
        var costs = Stream.of(cars1.get(true), cars2.get(true), cars3.get(true), cars4.get(true), cars5.get(true), cars6.get(true))
                .map(carList -> carList.stream().mapToInt(Car::getMass).sum())
                .map(mass -> perKg.multiply(BigDecimal.valueOf(mass))).toList();

        //Вывести суммарные стоимости в консоль.
        costs.forEach(System.out::println);
        // Вывести общую выручку логистической кампании.
        costs.stream().reduce(BigDecimal::add).ifPresent(System.out::println);
    }

    private static void task15() throws IOException {
        List<Flower> flowers = Util.getFlowers();
        flowers.stream().sorted(Comparator.comparing(Flower::getOrigin).reversed()
            .thenComparingInt(Flower::getPrice).reversed()
            .thenComparingDouble(Flower::getWaterConsumptionPerDay).reversed())
            .filter(flower -> flower.getCommonName().matches("^[c-sC-S].*"))
            .filter(flower -> flower.isShadePreferred()
                && flower.getFlowerVaseMaterial().contains("Glass")
                || flower.getFlowerVaseMaterial().contains("Aluminum")
                || flower.getFlowerVaseMaterial().contains("Steel")
            )
            //стоимость кубометра воды / 1000 * дней за 5 лет * потребление литров/день + стоисоть растения
            .map(flower -> BigDecimal.valueOf(1.39).divide(BigDecimal.valueOf(1000), RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(DAYS.between(LocalDate.now(), LocalDate.now().plusYears(5))))
                .multiply(BigDecimal.valueOf(flower.getWaterConsumptionPerDay()))
                .add(BigDecimal.valueOf(flower.getPrice())).setScale(2, RoundingMode.HALF_UP)
            )
            .reduce(BigDecimal::add).ifPresent(System.out::println);
    }
}