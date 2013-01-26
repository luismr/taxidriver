# Be sure to restart your server when you modify this file.

# Your secret key for verifying cookie session data integrity.
# If you change this key, all old sessions will become invalid!
# Make sure the secret is at least 30 characters and all random, 
# no regular words or you'll be exposed to dictionary attacks.
ActionController::Base.session = {
  :key         => '_taxidriver_api_session',
  :secret      => 'ba818af52ba757fce45c7673dc75589108620eca53a735160b26e62907f15525b95e1731b6a85bdeb9ef5738085ee9452a4407e9b919dbf3a66513fa963e957e'
}

# Use the database for sessions instead of the cookie-based default,
# which shouldn't be used to store highly confidential information
# (create the session table with "rake db:sessions:create")
# ActionController::Base.session_store = :active_record_store
