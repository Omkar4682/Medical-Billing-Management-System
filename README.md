# 💊 Medical Billing Management System.

A desktop-based Medical Billing Management System developed to simplify pharmacy operations such as medicine management, billing, sales, purchases, and invoice generation. The system helps medical store owners maintain medicine records, track inventory, and generate bills efficiently.

---

# 📌 Project Overview

The Medical Billing Management System is designed to automate the every Day activities of a medical store. It reduces manual work and provides an easy way to manage medicines, purchases, sales, and billing operations.

The system allows users to:

* Add new medicines to the inventory.
* View available medicines.
* Purchase medicines from suppliers and increse the stock.
* Sell medicines to customers decrese the stock.
* Generate invoices and billing receipts.
* Manage stock automatically after sales and purchases.

---

# ✨ Features

## 1. Show Medicines

* Display all available medicines in the inventory.
* Search medicines by:

  * Medicine ID
  * Medicine Name
  * Category
* View details such as:

  * Quantity
  * Price
  * Expiry Date
  * Manufacturer

---

## 2. Add Medicine

* Add new medicines to the database.
* Store information such as:

  * Medicine ID
  * Medicine Name
  * Category
  * Purchase Price
  * Selling Price
  * Quantity
  * Expiry Date
  * Supplier Information

---

## 3. Purchase Medicine

* Record medicine purchases from suppliers.
* Update stock quantities automatically.
* Maintain purchase history.
* Store supplier information and purchase dates.

---

## 4. Sale Medicine

* Sell medicines to customers.
* Search medicines quickly.
* Calculate total amount automatically.
* Reduce stock quantity after each sale.
* Prevent sales when stock is unavailable.

---

## 5. Invoice Generation

* Generate invoices for customer purchases.
* Include:

  * Invoice Number
  * Customer Details
  * Purchased Medicines
  * Quantity
  * Price
  * Total Amount
  * Date and Time

---

## 6. Billing System

* Calculate:

  * Subtotal
  * Taxes (if applicable)
  * Grand Total
* Print billing receipts.
* Maintain billing history.

---

# 🛠️ Technologies Used

| Technology   | Description                    |
| ------------ | ------------------------------ |
| Java         | Core application development   |
| Java Swing   | Graphical User Interface (GUI) |
| JDBC         | Database Connectivity          |
| MySQL        | Database Management System     |
| NetBeans IDE | Development Environment        |

---

# 📂 Project Modules

```
Medical Billing Management System
│
├── Login Module
├── Dashboard Module
├── Show Medicines Module
├── Add Medicine Module
├── Purchase Medicine Module
├── Sale Medicine Module
├── Invoice Module
├── Billing Module
└── Database Module
```

---

# 🗄️ Database Tables

## Medicine Table

* medicine_id
* medicine_name
* category
* purchase_price
* selling_price
* quantity
* expiry_date
* supplier_name

## Purchase Table

* purchase_id
* medicine_id
* quantity
* purchase_date
* supplier_name

## Sales Table

* sale_id
* medicine_id
* quantity
* sale_date
* total_amount

## Invoice Table

* invoice_id
* customer_name
* invoice_date
* total_amount

---

# ⚙️ System Requirements

### Hardware Requirements

* Processor: Intel i3 or above
* RAM: 4 GB or above
* Storage: 500 MB free space

### Software Requirements

* Windows 10/11
* JDK 8 or above
* NetBeans IDE
* MySQL Server

---

# 🚀 Installation Steps

1. Install Java JDK.
2. Install MySQL Server.
3. Install NetBeans IDE.
4. Import the project into NetBeans.
5. Create the database in MySQL.
6. Import the SQL file.
7. Configure database credentials.
8. Run the project.

---

# ▶️ How to Use

1. Open the application.
2. Login to the system.
3. Add medicines to the inventory.
4. Purchase medicines from suppliers.
5. Sell medicines to customers.
6. Generate invoices and billing receipts.
7. View medicine and sales records.

---

# 🎯 Advantages

* Easy inventory management.
* Reduces manual paperwork.
* Fast invoice generation.
* Automatic stock updates.
* User-friendly interface.
* Efficient billing management.

---

# 🔮 Future Enhancements

* Barcode Scanner Integration
* Email Invoice System
* Online Payment Integration
* Report Generation
* Multi-User Authentication
* Role-Based Access Control

---

# 👨‍💻 Developed By

**Omkar Date**

* B.Sc. Computer Science Graduate
* Python & Full Stack Developer

GitHub: https://github.com/Omkar4682

LinkedIn: https://www.linkedin.com/in/omkar-date-04969435b

---

# 📜 License

This project is developed for educational and academic purposes.
