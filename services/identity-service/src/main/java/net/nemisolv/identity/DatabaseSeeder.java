package net.nemisolv.identity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nemisolv.identity.entity.Permission;
import net.nemisolv.identity.entity.Role;
import net.nemisolv.identity.entity.User;
import net.nemisolv.identity.repository.PermissionRepository;
import net.nemisolv.identity.repository.RoleRepository;
import net.nemisolv.identity.repository.UserRepository;
import net.nemisolv.lib.core._enum.AuthProvider;
import net.nemisolv.lib.core._enum.PermissionName;
import net.nemisolv.lib.core._enum.RoleName;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    //    private final CategoryRepository categoryRepository;
//    private final ProductRepository productRepository;
//    private final InventoryRepository inventoryRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    //    private final BrandRepository brandRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting database seeding...");
        seedPermissions();
        seedRoles();
        seedAdminUser();
        seedOtherUsers();
        seedBrands();
        seedCategories();
        seedProductsAndInventory();
        log.info("Database seeding completed.");
    }

    private void seedOtherUsers() {
        if (userRepository.count() == 1) {
            log.info("Seeding other users...");

            Role managerRole = roleRepository.findByName(RoleName.MANAGER)
                    .orElseThrow(() -> new RuntimeException("Manager role not found"));

            Role staffRole = roleRepository.findByName(RoleName.STAFF)
                    .orElseThrow(() -> new RuntimeException("Staff role not found"));

            Role assistantRole = roleRepository.findByName(RoleName.ASSISTANT)
                    .orElseThrow(() -> new RuntimeException("Assistant role not found"));

            // Manager user
            User manager = User.builder()
                    .username("manager")
                    .email("manager@gmail.com")
                    .password(passwordEncoder.encode("manager"))
                    .role(managerRole)
                    .enabled(true)
                    .emailVerified(true)
                    .authProvider(AuthProvider.LOCAL)
                    .address("123 Manager St, Manager City")
                    .phoneNumber("1234567890")
                    .name("Jane")
                    .build();

            manager = userRepository.save(manager); // Save manager user
            log.info("Manager user created: email {}, password {}", manager.getEmail(), "manager");

            // Staff user
            User staff = User.builder()
                    .username("staff")
                    .email("staff@gmail.com")
                    .password(passwordEncoder.encode("staff"))
                    .role(staffRole)
                    .enabled(true)
                    .emailVerified(true)
                    .authProvider(AuthProvider.LOCAL)
                    .address("123 Staff St, Staff City")
                    .phoneNumber("9876543210")
                    .name("Smith")
                    .build();

            staff = userRepository.save(staff); // Save staff user
            log.info("Staff user created: email {}, password {}", staff.getEmail(), "staff");

            // Assistant user
            User assistant = User.builder()
                    .username("assistant")
                    .email("assistant@gmail.com")
                    .password(passwordEncoder.encode("assistant"))
                    .role(assistantRole)
                    .enabled(true)
                    .emailVerified(true)
                    .authProvider(AuthProvider.LOCAL)
                    .address("123 Assistant St, Assistant City")
                    .phoneNumber("5555555555")
                    .name("Doe")
                    .build();

            assistant = userRepository.save(assistant); // Save assistant user
            log.info("Assistant user created: email {}, password {}", assistant.getEmail(), "staff");

            log.info("Other users seeded.");
        }
    }


    private void seedPermissions() {
        if (permissionRepository.count() == 0) {
            log.info("Seeding permissions...");

            List<Permission> permissions = List.of(
                    // Role Management
                    Permission.builder().name(PermissionName.ASSIGN_ROLE).description(PermissionName.ASSIGN_ROLE.getDescription()).build(),

                    // User Management
                    Permission.builder().name(PermissionName.CREATE_USER).description(PermissionName.CREATE_USER.getDescription()).build(),
                    Permission.builder().name(PermissionName.UPDATE_USER).description(PermissionName.UPDATE_USER.getDescription()).build(),
                    Permission.builder().name(PermissionName.DELETE_USER).description(PermissionName.DELETE_USER.getDescription()).build(),
                    Permission.builder().name(PermissionName.VIEW_USER).description(PermissionName.VIEW_USER.getDescription()).build(),

                    // Product Management
                    Permission.builder().name(PermissionName.CREATE_PRODUCT).description(PermissionName.CREATE_PRODUCT.getDescription()).build(),
                    Permission.builder().name(PermissionName.UPDATE_PRODUCT).description(PermissionName.UPDATE_PRODUCT.getDescription()).build(),
                    Permission.builder().name(PermissionName.DELETE_PRODUCT).description(PermissionName.DELETE_PRODUCT.getDescription()).build(),
                    Permission.builder().name(PermissionName.VIEW_PRODUCT).description(PermissionName.VIEW_PRODUCT.getDescription()).build(),

                    // Inventory Management
                    Permission.builder().name(PermissionName.VIEW_INVENTORY).description(PermissionName.VIEW_INVENTORY.getDescription()).build(),
                    Permission.builder().name(PermissionName.UPDATE_INVENTORY).description(PermissionName.UPDATE_INVENTORY.getDescription()).build(),

                    // Order Management
                    Permission.builder().name(PermissionName.CREATE_ORDER).description(PermissionName.CREATE_ORDER.getDescription()).build(),
                    Permission.builder().name(PermissionName.UPDATE_ORDER).description(PermissionName.UPDATE_ORDER.getDescription()).build(),
                    Permission.builder().name(PermissionName.DELETE_ORDER).description(PermissionName.DELETE_ORDER.getDescription()).build(),
                    Permission.builder().name(PermissionName.VIEW_ORDER).description(PermissionName.VIEW_ORDER.getDescription()).build(),

                    // Report Management
                    Permission.builder().name(PermissionName.VIEW_SALES_REPORT).description(PermissionName.VIEW_SALES_REPORT.getDescription()).build(),
                    Permission.builder().name(PermissionName.VIEW_INVENTORY_REPORT).description(PermissionName.VIEW_INVENTORY_REPORT.getDescription()).build(),

                    // Inventory Management
                    Permission.builder().name(PermissionName.MANAGE_INVENTORY).description(PermissionName.MANAGE_INVENTORY.getDescription()).build(),
                    Permission.builder().name(PermissionName.UPDATE_STOCK).description(PermissionName.UPDATE_STOCK.getDescription()).build(),

                    // Brand Management
                    Permission.builder().name(PermissionName.CREATE_BRAND).description(PermissionName.CREATE_BRAND.getDescription()).build(),
                    Permission.builder().name(PermissionName.UPDATE_BRAND).description(PermissionName.UPDATE_BRAND.getDescription()).build(),
                    Permission.builder().name(PermissionName.DELETE_BRAND).description(PermissionName.DELETE_BRAND.getDescription()).build(),
                    Permission.builder().name(PermissionName.VIEW_BRAND).description(PermissionName.VIEW_BRAND.getDescription()).build()
            );

            permissionRepository.saveAll(permissions);
        }
    }


    private void seedRoles() {
        if (roleRepository.count() == 0) {
            log.info("Seeding roles...");
            List<Permission> allPermissions = permissionRepository.findAll();
            Role adminRole = Role.builder()
                    .name(RoleName.ADMIN)
                    .description("Administrator with all permissions")

                    .permissions(Set.copyOf(allPermissions))
                    .build();

            Role managerRole = Role.builder()
                    .name(RoleName.MANAGER)
                    .description("Manager with restricted permissions")
                    .permissions(allPermissions.stream()
                            .filter(p ->
                                            !p.getName().equals(PermissionName.DELETE_USER)
                                                    && !p.getName().equals(PermissionName.DELETE_PRODUCT)
                                                    && !p.getName().equals(PermissionName.DELETE_ORDER)
                                                    && !p.getName().equals(PermissionName.DELETE_BRAND)
//                                    && !p.getName().equals(PermissionName.ASSIGN_ROLE)
                            )

                            .collect(Collectors.toSet()))
                    .build();

            Role staffRole = Role.builder()
                    .name(RoleName.STAFF)
                    .description("Staff with view-only permissions")
                    .permissions(allPermissions.stream()
                            .filter(p -> p.getName().name().startsWith("VIEW_"))
                            .collect(Collectors.toSet()))
                    .build();

            Role assistantRole = Role.builder()
                    .name(RoleName.ASSISTANT)
                    .description("Assistant with limited permissions")
                    .permissions(allPermissions.stream()
                            .filter(p -> p.getName().name().startsWith("VIEW_")
                                    || p.getName().name().equals("CREATE_ORDER"))
                            .collect(Collectors.toSet()))
                    .build();

            Role customerRole = Role.builder()
                    .name(RoleName.CUSTOMER)
                    .description("Customer with limited permissions")
                    .permissions(allPermissions.stream()
                            .filter(p -> p.getName().name().startsWith("VIEW_")
                                    || p.getName().name().equals("CREATE_ORDER"))
                            .collect(Collectors.toSet()))
                    .build();

            roleRepository.saveAll(List.of(adminRole, managerRole, staffRole, assistantRole, customerRole));
        }
    }

    private void seedAdminUser() {
        if (userRepository.count() == 0) {
            log.info("Seeding admin user...");
            Role adminRole = roleRepository.findByName(RoleName.ADMIN)
                    .orElseThrow(() -> new RuntimeException("Admin role not found"));

            User admin = User.builder()
                    .username("admin")
                    .email("admin@techshop.com")
                    .password(passwordEncoder.encode("admin"))
                    .role(adminRole)
                    .enabled(true)
                    .emailVerified(true)
                    .authProvider(AuthProvider.LOCAL)
                    .address("123 Admin St, Admin City")
                    .phoneNumber("1234567890")
                    .name("John")
                    .build();

            userRepository.save(admin);
            log.info("Admin user created: email = admin@techshop.com, password = admin");
        }
    }

    private void seedBrands() {
//        if (brandRepository.count() == 0) {
//            log.info("Seeding brands...");
//            List<Brand> brands = List.of(
//                    Brand.builder().name("Apple").description("Premium tech devices").logoUrl("https://upload.wikimedia.org/wikipedia/commons/f/fa/Apple_logo_%28black%29.svg").build(),
//                    Brand.builder().name("Samsung").description("Innovative electronics").logoUrl("https://upload.wikimedia.org/wikipedia/commons/2/24/Samsung_Logo.svg").build(),
//                    Brand.builder().name("Dell").description("High-performance computers").logoUrl("https://upload.wikimedia.org/wikipedia/commons/a/a6/Logo_Dell_2020.png").build(),
//                    Brand.builder().name("Sony").description("Electronics and entertainment").logoUrl("https://upload.wikimedia.org/wikipedia/commons/8/84/Sony_logo_2021.svg").build(),
//                    Brand.builder().name("Lenovo").description("Reliable laptops and PCs").logoUrl("https://upload.wikimedia.org/wikipedia/commons/4/4c/Lenovo_logo_2020.svg").build(),
//                    Brand.builder().name("Asus").description("Gaming and versatile laptops").logoUrl("https://upload.wikimedia.org/wikipedia/commons/a/a9/Asus_logo_2021.svg").build(),
//                    Brand.builder().name("Generic").description("Unbranded or unknown products").logoUrl("https://upload.wikimedia.org/wikipedia/commons/a/a6/Logo_Dell_2020.png").build() // Using Dell logo as a placeholder for generic.
//            );
//            brandRepository.saveAll(brands);
//            log.info("Brands seeded.");
//        }
    }


    private void seedCategories() {
//        if (categoryRepository.count() == 0) {
//            log.info("Seeding categories...");
//            List<Category> categories = List.of(
//                    Category.builder().name("Electronics").description("Electronic gadgets and devices").build(),
//                    Category.builder().name("Clothing").description("Apparel and fashion items").build(),
//                    Category.builder().name("Home Appliances").description("Appliances for home use").build()
//            );
//            categoryRepository.saveAll(categories);
//        }
    }

    private void seedProductsAndInventory() {
//        if (productRepository.count() == 0) {
//            log.info("Seeding products and inventory...");
//
//            // Lấy danh mục từ database
//            Category electronics = getCategoryByName("Electronics");
//            Category clothing = getCategoryByName("Clothing");
//            Category homeAppliances = getCategoryByName("Home Appliances");
//
//            // Lấy thương hiệu từ database
//            Brand apple = getBrandByName("Apple");
//            Brand samsung = getBrandByName("Samsung");
//            Brand dell = getBrandByName("Dell");
//            Brand lenovo = getBrandByName("Lenovo");
//            Brand generic = getBrandByName("Generic");  // Hàng không có thương hiệu
//
//            // Tạo các sản phẩm
//            Product smartphone = Product.builder()
//                    .name("Smartphone")
//                    .description("High-end smartphone with the latest features")
//                    .price(BigDecimal.valueOf(799.99))
//                    .category(electronics)
//                    .brand(apple)
//                    .build();
//
//            Product laptop = Product.builder()
//                    .name("Laptop")
//                    .description("Powerful laptop for work and play")
//                    .price(BigDecimal.valueOf(1200.00))
//                    .category(electronics)
//                    .brand(dell)
//                    .build();
//
//            Product tShirt = Product.builder()
//                    .name("T-Shirt")
//                    .description("Comfortable cotton t-shirt")
//                    .price(BigDecimal.valueOf(19.99))
//                    .category(clothing)
//                    .brand(generic)  // Không có thương hiệu
//                    .build();
//
//            Product vacuumCleaner = Product.builder()
//                    .name("Vacuum Cleaner")
//                    .description("High-power vacuum cleaner")
//                    .price(BigDecimal.valueOf(150.00))
//                    .category(homeAppliances)
//                    .brand(lenovo)
//                    .build();
//
//            // Lưu các sản phẩm vào database
//            productRepository.saveAll(List.of(smartphone, laptop, tShirt, vacuumCleaner));
//
//            // Tạo các bản ghi kho (Inventory) cho từng sản phẩm
//            List<Inventory> inventories = List.of(
//                    Inventory.builder().product(smartphone).quantity(50).build(),
//                    Inventory.builder().product(laptop).quantity(40).build(),
//                    Inventory.builder().product(tShirt).quantity(100).build(),
//                    Inventory.builder().product(vacuumCleaner).quantity(30).build()
//            );
//
//            // Lưu kho vào database
//            inventoryRepository.saveAll(inventories);
//            log.info("Products and inventory seeded.");
//        }
    }


//    private Category getCategoryByName(String name) {
//        return categoryRepository.findByNameIgnoreCase(name)
//                .orElseThrow(() -> new RuntimeException("Category not found: " + name));
//    }

//    private Brand getBrandByName(String name) {
//        return brandRepository.findByNameIgnoreCase(name)
//                .orElseThrow(() -> new RuntimeException("Brand not found: " + name));
//    }
}
