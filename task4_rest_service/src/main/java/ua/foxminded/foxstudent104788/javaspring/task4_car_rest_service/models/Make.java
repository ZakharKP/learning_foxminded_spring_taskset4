package ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models;

import java.util.Arrays;

public enum Make {

    LEXUS("Lexus"),
    SUBARU("Subaru"),
    CHEVROLET("Chevrolet"),
    CHRYSLER("Chrysler"),
    MASERATI("Maserati"),
    FIAT("FIAT"),
    FREIGHTLINER("Freightliner"),
    SUZUKI("Suzuki"),
    MAZDA("MAZDA"),
    FISKER("Fisker"),
    RIVIAN("Rivian"),
    GMC("GMC"),
    LINCOLN("Lincoln"),
    HYUNDAI("Hyundai"),
    EAGLE("Eagle"),
    ASTON_MARTIN("Aston Martin"),
    ACURA("Acura"),
    KIA("Kia"),
    PONTIAC("Pontiac"),
    PLYMOUTH("Plymouth"),
    SAAB("Saab"),
    GEO("Geo"),
    MAYBACH("Maybach"),
    TOYOTA("Toyota"),
    JAGUAR("Jaguar"),
    TESLA("Tesla"),
    MERCEDES_BENZ("Mercedes-Benz"),
    PANOZ("Panoz"),
    DAEWOO("Daewoo"),
    VOLKSWAGEN("Volkswagen"),
    VOLVO("Volvo"),
    SATURN("Saturn"),
    DODGE("Dodge"),
    AUDI("Audi"),
    ALFA_ROMEO("Alfa Romeo"),
    HONDA("Honda"),
    SMART("smart"),
    FERRARI("Ferrari"),
    SRT("SRT"),
    PORSCHE("Porsche"),
    SCION("Scion"),
    OLDSMOBILE("Oldsmobile"),
    ISUZU("Isuzu"),
    INFINITI("INFINITI"),
    ROLLS_ROYCE("Rolls-Royce"),
    JEEP("Jeep"),
    BUICK("Buick"),
    POLESTAR("Polestar"),
    MITSUBISHI("Mitsubishi"),
    LOTUS("Lotus"),
    BMW("BMW"),
    BENTLEY("Bentley"),
    CADILLAC("Cadillac"),
    LAND_ROVER("Land Rover"),
    LAMBORGHINI("Lamborghini"),
    DAIHATSU("Daihatsu"),
    MINI("MINI"),
    HUMMER("HUMMER"),
    GENESIS("Genesis"),
    FORD("Ford"),
    MCLAREN("McLaren"),
    MERCURY("Mercury"),
    NISSAN("Nissan"),
    RAM("Ram");

    private String makeName;

    Make(String makeName) {
	this.makeName = makeName;
    }

    public String getMakeName() {
	return makeName;
    }

    public static Make get(String makeName) {
	return Arrays.stream(Make.values()).filter(x -> x.getMakeName().equals(makeName)).findFirst().orElse(null);
    }
}
