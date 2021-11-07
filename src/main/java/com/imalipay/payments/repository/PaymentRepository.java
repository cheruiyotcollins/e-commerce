
package com.imalipay.payments.repository;

import com.imalipay.payments.model.PaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentRepository extends JpaRepository<PaymentModel,Long>{

//    @Query("SELECT p FROM Product p WHERE p.category=?1")
//    List<PaymentModel> getByCategory(@Param("category") String category);


}
