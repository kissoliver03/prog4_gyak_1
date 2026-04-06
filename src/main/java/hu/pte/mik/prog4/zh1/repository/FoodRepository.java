package hu.pte.mik.prog4.zh1.repository;

import hu.pte.mik.prog4.zh1.model.Food;
import hu.pte.mik.prog4.zh1.util.IdProvider;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FoodRepository {

    private static final FoodRepository INSTANCE = new FoodRepository();

    private final Map<Long, Food> storage;

    private final IdProvider idProvider = IdProvider.getInstance();

    private FoodRepository() {
        this.storage = Stream.of(new Food(this.idProvider.getNewId(), "Étterem_1", "Étel_1", "1000"),
                new Food(this.idProvider.getNewId(), "Étterem_2", "Étel_2", "2000"),
                new Food(this.idProvider.getNewId(), "Étterem_3", "Étel_3", "3000"),
                new Food(this.idProvider.getNewId(), "Étterem_4", "Étel_4", "4000"),
                new Food(this.idProvider.getNewId(), "Étterem_5", "Étel_5", "5000"))
                .collect(Collectors.toMap(Food::getId, Function.identity()));
    }

    public static FoodRepository getInstance() {
        return INSTANCE;
    }

    public Food findById(Long id){
        return storage.get(id);
    }

    public List<Food> findAll(){
        return List.copyOf(this.storage.values());
    }

    public Food create(String restaurantName, String foodName, String price){
        return this.storage.put(this.idProvider.getNewId(), new Food(idProvider.getNewId(), restaurantName, foodName, price));
    }

    public Food delete(Long id){
        return this.storage.remove(id);
    }
}
