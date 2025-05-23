package hw_4;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamTask1 {
    public static void main(String[] args) {
        List<Order> orders = List.of(
                new Order("Laptop", 1200.0),
                new Order("Smartphone", 800.0),
                new Order("Laptop", 1500.0),
                new Order("Tablet", 500.0),
                new Order("Smartphone", 900.0)
        );

        /*
        * - Создайте список заказов с разными продуктами и их стоимостями.
          - Группируйте заказы по продуктам.
          - Для каждого продукта найдите общую стоимость всех заказов.
          - Отсортируйте продукты по убыванию общей стоимости.
          - Выберите три самых дорогих продукта.
          - Выведите результат: список трех самых дорогих продуктов и их общая стоимость.
          * */

        orders.stream()
                .collect(Collectors.groupingBy(Order::getProduct,
                        Collectors.summingDouble(Order::getCost)))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(3)
                .toList()
                .forEach(System.out::println);
    }

    static class Order {
        private final String product;
        private final double cost;

        public Order(String product, double cost) {
            this.product = product;
            this.cost = cost;
        }

        public String getProduct() {
            return product;
        }

        public double getCost() {
            return cost;
        }

        @Override
        public String toString() {
            return "Order [product = " + product + ", cost = " + cost + "]";
        }
    }
}

