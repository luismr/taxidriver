class CreateSpots < ActiveRecord::Migration
  def self.up
    create_table :spots do |t|
      t.string :name
      t.text :address
      t.string :city
      t.string :state
      t.string :phone
      t.string :spot_type
      t.float :map_lat
      t.float :map_long

      t.timestamps
    end
  end

  def self.down
    drop_table :spots
  end
end
