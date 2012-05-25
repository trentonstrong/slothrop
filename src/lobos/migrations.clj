(ns lobos.migrations
  (:refer-clojure :exclude [alter drop
                           bigint boolean char double float time])
  (:use (lobos [migration :only [defmigration]]
               core
               schema)))

(defmigration add-books-table
  "Table to store primary information about a book.  Includes identifier and primary metadata"
  (up [] (create
          (table :books (integer :id :auto-inc :primary-key)
            (text :dc_identifier :unique)
            (text :primary_title)
            (text :primary_language)
            (text :primary_source)
            (text :location_url :not-null))))
  (down [] (drop (table :books))))

(defmigration add-books-authors-table
  "Table to relate book authors to a book entity, since authors are queryable in the application.  Note that authors are not fully normalized.  This is a design choice since we have no way of differentiating two authors with the same name, so we take the authors to be an artifact of the dublin core metadata provided."
  (up [] (create
          (table :books_authors (integer :id :auto-inc :primary-key)
                 (integer :book_id
                          [:refer :books :id :on-delete :cascade])
                 (text :name))))
  (down [] (drop (table :books_authors))))

(defmigration add-books-subjects-table
  "Book subjects are semantic metadata concerning the topics related to a book.  These fulfill a central role in relating and categorizing books for users of the system, hence their own table"
  (up [] (create
          (table :books_subjects (integer :id :auto-inc :primary-key)
                 (integer :book_id
                          [:refer :books :id :on-delete :cascade])
                 (text :subject))))
  (down [] (drop (table :books_subjects))))