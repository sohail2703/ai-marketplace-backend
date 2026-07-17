package com.aimarketplace.repository;

import com.aimarketplace.entity.User;
import com.aimarketplace.entity.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface UserSubscriptionRepository
        extends JpaRepository<UserSubscription, Long> {


    List<UserSubscription> findByUser(User user);

}