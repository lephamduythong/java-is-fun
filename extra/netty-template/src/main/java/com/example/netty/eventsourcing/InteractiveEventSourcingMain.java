package com.example.netty.eventsourcing;

import com.example.netty.eventsourcing.aggregate.ProductAggregate;
import com.example.netty.eventsourcing.event.DomainEvent;
import com.example.netty.eventsourcing.handler.AuditLogEventHandler;
import com.example.netty.eventsourcing.handler.EventBus;
import com.example.netty.eventsourcing.handler.StatisticsEventHandler;
import com.example.netty.eventsourcing.interf.IEventStore;
import com.example.netty.eventsourcing.repository.EventSourcedProductRepository;
import com.example.netty.eventsourcing.store.InMemoryEventStore;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Scanner;

/**
 * Interactive Event Sourcing demo
 * Allows users to perform operations and see events in real-time
 */
public class InteractiveEventSourcingMain {

    private static final IEventStore eventStore = new InMemoryEventStore();
    private static final EventSourcedProductRepository repository = new EventSourcedProductRepository(eventStore);
    private static final EventBus eventBus = new EventBus();
    private static final StatisticsEventHandler statsHandler = new StatisticsEventHandler();

    public static void main(String[] args) {
        // Setup event handlers
        eventBus.register(new AuditLogEventHandler());
        eventBus.register(statsHandler);

        System.out.println("=== Interactive Event Sourcing Demo ===\n");
        
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Select option: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                System.out.println();

                switch (choice) {
                    case 1:
                        createProduct(scanner);
                        break;
                    case 2:
                        updateProduct(scanner);
                        break;
                    case 3:
                        viewProduct(scanner);
                        break;
                    case 4:
                        viewProductHistory(scanner);
                        break;
                    case 5:
                        timeTravel(scanner);
                        break;
                    case 6:
                        deleteProduct(scanner);
                        break;
                    case 7:
                        viewAllEvents();
                        break;
                    case 8:
                        statsHandler.printStatistics();
                        break;
                    case 0:
                        running = false;
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            
            System.out.println();
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n========== Menu ==========");
        System.out.println("1. Create Product");
        System.out.println("2. Update Product");
        System.out.println("3. View Product (current state)");
        System.out.println("4. View Product History");
        System.out.println("5. Time Travel (view at version)");
        System.out.println("6. Delete Product");
        System.out.println("7. View All Events");
        System.out.println("8. View Statistics");
        System.out.println("0. Exit");
        System.out.println("==========================");
    }

    private static void createProduct(Scanner scanner) {
        System.out.print("Product name: ");
        String name = scanner.nextLine();
        
        System.out.print("Description: ");
        String description = scanner.nextLine();
        
        System.out.print("Price: ");
        BigDecimal price = new BigDecimal(scanner.nextLine());
        
        System.out.print("Quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        ProductAggregate product = ProductAggregate.create(name, description, price, quantity);
        repository.save(product);
        eventBus.publishAll(eventStore.getEventsForAggregate(product.getId()));

        System.out.println("\n✓ Product created with ID: " + product.getId());
        System.out.println(product);
    }

    private static void updateProduct(Scanner scanner) {
        System.out.print("Product ID: ");
        String id = scanner.nextLine();

        Optional<ProductAggregate> productOpt = repository.findById(id);
        if (!productOpt.isPresent()) {
            System.out.println("Product not found!");
            return;
        }

        ProductAggregate product = productOpt.get();
        System.out.println("\nCurrent state: " + product);
        
        System.out.println("\nWhat to update?");
        System.out.println("1. Name");
        System.out.println("2. Price");
        System.out.println("3. Quantity");
        System.out.print("Choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                System.out.print("New name: ");
                String newName = scanner.nextLine();
                product.changeName(newName);
                break;
            case 2:
                System.out.print("New price: ");
                BigDecimal newPrice = new BigDecimal(scanner.nextLine());
                product.changePrice(newPrice);
                break;
            case 3:
                System.out.print("Quantity adjustment (+/-): ");
                int adjustment = Integer.parseInt(scanner.nextLine());
                product.adjustQuantity(adjustment);
                break;
        }

        repository.save(product);
        eventBus.publishAll(product.getUncommittedEvents());
        
        System.out.println("\n✓ Product updated");
        System.out.println(product);
    }

    private static void viewProduct(Scanner scanner) {
        System.out.print("Product ID: ");
        String id = scanner.nextLine();

        Optional<ProductAggregate> productOpt = repository.findById(id);
        if (productOpt.isPresent()) {
            System.out.println("\n" + productOpt.get());
        } else {
            System.out.println("Product not found!");
        }
    }

    private static void viewProductHistory(Scanner scanner) {
        System.out.print("Product ID: ");
        String id = scanner.nextLine();

        java.util.List<DomainEvent> history = repository.getHistory(id);
        if (history.isEmpty()) {
            System.out.println("No events found for this product!");
            return;
        }

        System.out.println("\n=== Event History (" + history.size() + " events) ===");
        for (int i = 0; i < history.size(); i++) {
            DomainEvent event = history.get(i);
            System.out.println("[" + (i + 1) + "] v" + event.getVersion() + " - " + 
                             event.getEventType() + " at " + event.getOccurredOn());
        }
    }

    private static void timeTravel(Scanner scanner) {
        System.out.print("Product ID: ");
        String id = scanner.nextLine();
        
        System.out.print("Version: ");
        long version = Long.parseLong(scanner.nextLine());

        Optional<ProductAggregate> productOpt = repository.findByIdAtVersion(id, version);
        if (productOpt.isPresent()) {
            System.out.println("\n=== Product at Version " + version + " ===");
            System.out.println(productOpt.get());
        } else {
            System.out.println("Product not found or version doesn't exist!");
        }
    }

    private static void deleteProduct(Scanner scanner) {
        System.out.print("Product ID: ");
        String id = scanner.nextLine();

        Optional<ProductAggregate> productOpt = repository.findById(id);
        if (!productOpt.isPresent()) {
            System.out.println("Product not found!");
            return;
        }

        ProductAggregate product = productOpt.get();
        
        System.out.print("Deletion reason: ");
        String reason = scanner.nextLine();

        product.delete(reason);
        repository.save(product);
        eventBus.publishAll(product.getUncommittedEvents());

        System.out.println("\n✓ Product deleted");
    }

    private static void viewAllEvents() {
        java.util.List<DomainEvent> allEvents = eventStore.getAllEvents();
        
        if (allEvents.isEmpty()) {
            System.out.println("No events in store!");
            return;
        }

        System.out.println("\n=== All Events (" + allEvents.size() + ") ===");
        for (int i = 0; i < allEvents.size(); i++) {
            DomainEvent event = allEvents.get(i);
            System.out.println("[" + (i + 1) + "] " + event.getEventType() + 
                             " - Aggregate: " + event.getAggregateId().substring(0, 8) + "... " +
                             "(v" + event.getVersion() + ")");
        }
    }
}
