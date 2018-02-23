package com.alexsnowm.grocerycomparer.models.data;

import com.alexsnowm.grocerycomparer.models.Price;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PriceDao extends CrudRepository<Price, Integer> {
}
