package com.alexsnowm.grocerycomparer.models.data;

import com.alexsnowm.grocerycomparer.models.Store;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface StoreDao extends CrudRepository<Store, Integer> {
}
