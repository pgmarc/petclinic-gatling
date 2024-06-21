import csv


def generateUniqueData(filename: str, numUsers: int, useCase: str):
    with open(filename, "w", newline="") as csvfile:
        users_writer = csv.writer(csvfile, delimiter=",", quoting=csv.QUOTE_MINIMAL)
        users_writer.writerow(["username", "petName"])
        for i in range(numUsers):
            users_writer.writerow([useCase + str(i), useCase + "Pet" + str(i)])

RESOURCES_PATH = "src/test/resources"
CONCURRENT_OWNERS_FILE = "owners-con"
RAMP_OWNERS_FILE = "owners-ramp"
IDLE_OWNERS_FILE = "owners-idle"


# Basic owners
generateUniqueData(f'{RESOURCES_PATH}/basic/{CONCURRENT_OWNERS_FILE}.csv', 1_000, "BasicCon")
generateUniqueData(f'{RESOURCES_PATH}/basic/{RAMP_OWNERS_FILE}.csv', 1_000, "BasicRamp")
generateUniqueData(f'{RESOURCES_PATH}/basic/{IDLE_OWNERS_FILE}.csv', 1_000, "BasicIdle")

# Gold owners
generateUniqueData(f'{RESOURCES_PATH}/gold/{CONCURRENT_OWNERS_FILE}.csv', 1_000, "GoldCon")
generateUniqueData(f'{RESOURCES_PATH}/gold/{RAMP_OWNERS_FILE}.csv', 1_000, "GoldRamp")
generateUniqueData(f'{RESOURCES_PATH}/gold/{IDLE_OWNERS_FILE}.csv', 1_000, "GoldIdle")

# Platinum owners
generateUniqueData(f'{RESOURCES_PATH}/platinum/{CONCURRENT_OWNERS_FILE}.csv', 1_000, "PlatinumCon")
generateUniqueData(f'{RESOURCES_PATH}/platinum/{RAMP_OWNERS_FILE}.csv', 1_000, "PlatinumRamp")
generateUniqueData(f'{RESOURCES_PATH}/platinum/{IDLE_OWNERS_FILE}.csv', 1_000, "PlatinumIdle")

# Final simulation
generateUniqueData(f'{RESOURCES_PATH}/pricing/basic-{CONCURRENT_OWNERS_FILE}-final.csv', 10_000, "BasicConFinal")
generateUniqueData(f'{RESOURCES_PATH}/pricing/gold-{CONCURRENT_OWNERS_FILE}-final.csv', 5_000, "GoldConFinal")
generateUniqueData(f'{RESOURCES_PATH}/pricing/platinum-{CONCURRENT_OWNERS_FILE}-final.csv', 1_000, "PlatinumConFinal")
generateUniqueData(f'{RESOURCES_PATH}/pricing/basic-{RAMP_OWNERS_FILE}-final.csv', 10_000, "BasicRampFinal")
generateUniqueData(f'{RESOURCES_PATH}/pricing/gold-{RAMP_OWNERS_FILE}-final.csv', 5_000, "GoldRampFinal")
generateUniqueData(f'{RESOURCES_PATH}/pricing/platinum-{RAMP_OWNERS_FILE}-final.csv', 1_000, "PlatinumRampFinal")

