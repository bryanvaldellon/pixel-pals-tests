@scalability
Feature: Pixel Pals Scalability Testing
#  assesses its ability to handle increasing user loads and data volumes by scaling resources
#  such as servers and databases dynamically,
#  ensuring consistent performance and availability as traffic grows

  @loadBalancing
  Scenario: Load Balancing Testing
    Given the Pixel Pals website is deployed across multiple servers
    When the website experiences heavy concurrent traffic
    Then the load balancer should distribute incoming requests evenly across servers
    And all servers should handle requests effectively without overloading or becoming unresponsive

  @autoScaling
  Scenario: Auto-scaling Testing
    Given the Pixel Pals website is configured for auto-scaling
    When the website experiences sudden spikes in traffic
    Then additional resources (e.g., servers, database instances) should automatically provision to handle the increased load
    And resources should scale back down when traffic returns to normal levels

  @geographicScaling
  Scenario: Geographic Scaling Testing
    Given the Pixel Pals website serves users from different geographic regions
    When the website experiences high traffic from specific regions
    Then content delivery networks (CDNs) should distribute content efficiently to users in those regions
    And localized servers should handle requests to reduce latency and improve performance for users in each region