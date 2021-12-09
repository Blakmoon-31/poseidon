package com.nnk.springboot.poseidon.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import com.nnk.springboot.poseidon.domain.BidList;
import com.nnk.springboot.poseidon.domain.CurvePoint;
import com.nnk.springboot.poseidon.domain.Rating;
import com.nnk.springboot.poseidon.domain.RuleName;
import com.nnk.springboot.poseidon.domain.Trade;
import com.nnk.springboot.poseidon.domain.User;
import com.nnk.springboot.poseidon.dto.BidListDto;
import com.nnk.springboot.poseidon.dto.CurvePointDto;
import com.nnk.springboot.poseidon.dto.RatingDto;
import com.nnk.springboot.poseidon.dto.RuleNameDto;
import com.nnk.springboot.poseidon.dto.TradeDto;
import com.nnk.springboot.poseidon.dto.UserDto;
import com.nnk.springboot.poseidon.dto.UserListDto;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

	BidListDto bidListToBidListDto(BidList bidList);

	BidList bidListDtoToBidList(BidListDto bidListDto);

	Collection<BidListDto> bidListsToBidListDtos(Collection<BidList> bidLists);

	CurvePointDto curvePointToCurvePointDto(CurvePoint curvePoint);

	CurvePoint curvePointDtoToCurvePoint(CurvePointDto curvePointDto);

	Collection<CurvePointDto> curvePointsToCurvePointDtos(Collection<CurvePoint> bidLists);

	RatingDto ratingToRatingDto(Rating rating);

	Rating ratingDtoToRating(RatingDto ratingDto);

	Collection<RatingDto> ratingsToRatingDtos(Collection<Rating> bidLists);

	RuleNameDto ruleNameToRuleNameDto(RuleName ruleName);

	RuleName ruleNameDtoToRuleName(RuleNameDto ruleNameDto);

	Collection<RuleNameDto> ruleNamesToRuleNameDtos(Collection<RuleName> bidLists);

	TradeDto tradeToTradeDto(Trade trade);

	Trade tradeDtoToTrade(TradeDto tradeDto);

	Collection<TradeDto> tradesToTradeDtos(Collection<Trade> bidLists);

	UserDto userToUserDto(User user);

	User userDtoToUser(UserDto userDto);

	UserListDto userToUserListDto(User user);

	Collection<UserListDto> usersToUserDtos(Collection<User> bidLists);

}
