package ru.skb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import ru.skb.entities.Statement;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Long> {

    List<Statement> getAllByIsSended(boolean status);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE statement s SET is_sended = true WHERE id in (:ids)")
    void updateAllSendedStatus(List<Long> ids);
}
