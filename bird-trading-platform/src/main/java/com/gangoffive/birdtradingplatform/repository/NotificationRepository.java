package com.gangoffive.birdtradingplatform.repository;

import com.gangoffive.birdtradingplatform.entity.Notification;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Page<Notification> findAllByNotiDateAfter(Date dateAfter, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE tblNotification n SET n.seen = true WHERE n.id IN :id")
    int updateNotificationsById(@Param("id") long id);
}
