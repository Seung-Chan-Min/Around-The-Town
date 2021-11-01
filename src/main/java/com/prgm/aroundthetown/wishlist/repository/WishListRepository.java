package com.prgm.aroundthetown.wishlist.repository;

import com.prgm.aroundthetown.wishlist.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Long> {
}
