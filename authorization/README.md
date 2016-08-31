# Authorization Server
Listing most common features of the authorization server application as following...

    1. Registration														DONE
    2. Login																	DONE
    3. Logout																DONE
    4. Deactivate account												DONE
    5. Update user data
    	 a) Profile picture												DONE
    	 b) Password															DONE
    	 c) Status (verified, active, inactive etc)					DONE
    	 d) Role																----
    	 e) Resend Confirmation/Verification Mail					----
    	 f) Any other information										DONE
    6. Role Management (by admin only)
    	 a) Insert new role
    	 b) Update role
    	 c) Delete/Deactivate Role
    7. Service/Permissions/Access/Feature Management
    	 a) Insert new service (by admin only)
    	 b) Stop service (by admin only)
    	 c) Service mapping
    	 	 (i) User wise
    	 	 (ii) Role wise
    8. Mysql event schedulers
    	a) To expire sessions in sessions table
    	b) To expire userKey in users table
    
