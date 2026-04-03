package hu.pte.mik.prog4.zh1.service;

import hu.pte.mik.prog4.zh1.model.Food;
import hu.pte.mik.prog4.zh1.repository.FoodRepository;

import java.util.List;
import java.util.logging.Logger;

public class FoodService {

    private static final Logger LOGGER = Logger.getLogger(FoodService.class.toString());

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository.getInstance();
    }

    public Food findById(Long id){
        return this.foodRepository.findById(id);
    }

    public List<Food> findAll(){
        return this.foodRepository.findAll();
    }

    public Food create(String restaurantName, String foodName, String price){
        return this.foodRepository.create(restaurantName, foodName, price);
    }

    public Food deleteById(Long id){
        return this.foodRepository.delete(id);
    }



}
