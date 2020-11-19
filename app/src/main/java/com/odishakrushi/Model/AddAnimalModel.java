package com.odishakrushi.Model;

public class AddAnimalModel {

   private String animalName;
   private String animal_variety;
    private String animal_numbers;

  /*  public AddAnimalModel(String animalName, String animal_variety, String animal_numbers) {
        this.animalName = animalName;
        this.animal_variety = animal_variety;
        this.animal_numbers = animal_numbers;
    }*/

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public String getAnimal_variety() {
        return animal_variety;
    }

    public void setAnimal_variety(String animal_variety) {
        this.animal_variety = animal_variety;
    }

    public String getAnimal_numbers() {
        return animal_numbers;
    }

    public void setAnimal_numbers(String animal_numbers) {
        this.animal_numbers = animal_numbers;
    }
}