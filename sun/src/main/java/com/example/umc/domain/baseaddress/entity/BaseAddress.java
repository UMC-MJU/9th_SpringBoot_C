package com.example.umc.domain.baseaddress.entity;

import com.example.umc.domain.member.entity.Member;
import com.example.umc.domain.restaurant.entity.Restaurant;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "base_address")
public class BaseAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "base_address_id")
    private Long id;

    @Column(name = "base_address_name", nullable = false)
    private String baseAddressName;

    @OneToMany(mappedBy = "baseAddress", cascade = CascadeType.ALL)
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "baseAddress", cascade = CascadeType.ALL)
    private List<Restaurant> restaurants = new ArrayList<>();
}
