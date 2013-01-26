# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ name: 'Chicago' }, { name: 'Copenhagen' }])
#   Mayor.create(name: 'Emanuel', city: cities.first)

require 'csv'

spots = "#{Rails.root}/db/spots.csv"
n = 0

CSV.foreach("#{spots}") do |row|
	n += 1
	next if n == 1 or row.join.blank?

	spot_name = row[0]
	spot_address = row[1]
	spot_city = row[2]
  	spot_state = row[3]
	spot_phone = row[4]
	spot_type = row[5]
	spot_map_lat = row[6]
	spot_map_long = row[7]

	spot = Spot.create(:name => spot_name,
					:address => spot_address,
					:city => spot_city,
					:state => spot_state,
					:phone => spot_phone,
					:spot_type => spot_type,
					:map_lat => spot_map_lat,
					:map_long => spot_map_long)
	spot.save
end
