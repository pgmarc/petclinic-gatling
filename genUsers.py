import csv


def generateUniqueData(filename: str, header: list[str], numUsers: int, userType: str):
    with open(filename, "w", newline="") as csvfile:
        users_writer = csv.writer(csvfile, delimiter=",", quoting=csv.QUOTE_MINIMAL)
        users_writer.writerow(header)
        for i in range(numUsers):
            users_writer.writerow([userType + str(i), userType + "Pet" + str(i)])

RESOURCES_PATH = "src/test/resources/"
CONCURRENT_OWNERS_FILE = "owners-con.csv"
RAMP_OWNERS_FILE = "owners-ramp.csv"
IDLE_OWNERS_FILE = "owners-idle.csv"

# Basic owners
generateUniqueData( RESOURCES_PATH + "basic/" + CONCURRENT_OWNERS_FILE, ["username", "petName"], 1_000, "BasicCon")
generateUniqueData( RESOURCES_PATH + "basic/" +  RAMP_OWNERS_FILE, ["username", "petName"], 1_000, "BasicRamp")
generateUniqueData( RESOURCES_PATH + "basic/" +  IDLE_OWNERS_FILE, ["username", "petName"], 1_000, "BasicIdle")

# Gold owners
generateUniqueData(RESOURCES_PATH + "gold/" + CONCURRENT_OWNERS_FILE, ["username", "petName"], 1_000, "GoldCon")
generateUniqueData(RESOURCES_PATH + "gold/" + RAMP_OWNERS_FILE, ["username", "petName"], 1_000, "GoldRamp")
generateUniqueData(RESOURCES_PATH + "gold/" + IDLE_OWNERS_FILE, ["username", "petName"], 1_000, "GoldIdle")

# Platinum owners
generateUniqueData(RESOURCES_PATH + "platinum/" + CONCURRENT_OWNERS_FILE, ["username", "petName"], 1_000, "PlatinumCon")
generateUniqueData(RESOURCES_PATH + "platinum/" + RAMP_OWNERS_FILE, ["username", "petName"], 1_000, "PlatinumRamp")
generateUniqueData(RESOURCES_PATH + "platinum/" + IDLE_OWNERS_FILE, ["username", "petName"], 1_000, "PlatinumIdle")

# Final simulation
generateUniqueData(RESOURCES_PATH + "pricing/" + "basic-owners-con-final.csv", ["username", "petName"], 10_000, "BasicConFinal")
generateUniqueData(RESOURCES_PATH + "pricing/" + "gold-owners-con-final.csv", ["username", "petName"], 5_000, "GoldConFinal")
generateUniqueData(RESOURCES_PATH + "pricing/" + "platinum-owners-con-final.csv", ["username", "petName"], 1_000, "PlatinumConFinal")
generateUniqueData(RESOURCES_PATH + "pricing/" + "basic-owners-ramp-final.csv", ["username", "petName"], 10_000, "BasicRampFinal")
generateUniqueData(RESOURCES_PATH + "pricing/" + "gold-owners-ramp-final.csv", ["username", "petName"], 5_000, "GoldRampFinal")
generateUniqueData(RESOURCES_PATH + "pricing/" + "platinum-owners-ramp-final.csv", ["username", "petName"], 1_000, "PlatinumRampFinal")

