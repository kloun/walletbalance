package me.cetjs2.walletbalance.exceptions;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.time.LocalDateTime;
import me.cetjs2.walletbalance.dto.ErrorResponse; // Создай этот рекорд (см. ниже)
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // 1. Когда кошелек не найден (404)
  @ExceptionHandler(WalletNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ApiResponse(
      responseCode = "404",
      description = "Кошелек отсутствует в базе",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  public ErrorResponse handleNotFound(WalletNotFoundException ex) {
    return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now());
  }

  // 2. Когда денег мало (400)
  @ExceptionHandler(InsufficientFundsException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ApiResponse(
      responseCode = "400",
      description = "Недостаточно средств на балансе",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  public ErrorResponse handleInsufficientFunds(InsufficientFundsException ex) {
    return new ErrorResponse(
        HttpStatus.BAD_REQUEST.value(),
        "Insufficient funds: " + ex.getMessage(),
        LocalDateTime.now());
  }

  // 3. Когда передали кривой формат UUID или отрицательное число (400)
  @ExceptionHandler({
    IllegalArgumentException.class,
    org.springframework.web.bind.MethodArgumentNotValidException.class
  })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ApiResponse(
      responseCode = "400",
      description = "Ошибка валидации входных данных",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  public ErrorResponse handleBadRequest(Exception ex) {
    return new ErrorResponse(
        HttpStatus.BAD_REQUEST.value(),
        "Invalid request data: " + ex.getMessage(),
        LocalDateTime.now());
  }
}
