The used API for the images will be dog.ceo, or Dog API.

There are 3 main API calls:

To get absolutely random images, use https://dog.ceo/api/breeds/image/random/3. The number at the end is the number of image URLs that will be returned
This can generate the following JSON:
{
    "message": [
        "https://images.dog.ceo/breeds/terrier-andalusian/El-Bodeguero-Andaluz-caracteristicas-de-un-perro-ratonero-perro-cuatro-patas_872f0926-e6bc-4916-913d-5a3156c6fba0_480x480.jpg",
        "https://images.dog.ceo/breeds/terrier-scottish/n02097298_8858.jpg",
        "https://images.dog.ceo/breeds/komondor/n02105505_4180.jpg"
    ],
    "status": "success"
}

To get a list of dog breeds, use https://dog.ceo/api/breeds/list/all.
This will return the following JSON:
{
    "message": {
        "affenpinscher": [],
        "african": [
            "wild"
        ],
        "airedale": [],
        "akita": [],
        "appenzeller": [],
        "australian": [
            "kelpie",
            "shepherd"
        ],
        "bakharwal": [
            "indian"
        ],
        "basenji": [],
        "beagle": [],
        "bluetick": [],
        "borzoi": [],
        "bouvier": [],
        "boxer": [],
        "brabancon": [],
        "briard": [],
        "buhund": [
            "norwegian"
        ],
        "bulldog": [
            "boston",
            "english",
            "french"
        ],
        "bullterrier": [
            "staffordshire"
        ],
        "cattledog": [
            "australian"
        ],
        "cavapoo": [],
        "chihuahua": [],
        "chippiparai": [
            "indian"
        ],
        "chow": [],
        "clumber": [],
        "cockapoo": [],
        "collie": [
            "border"
        ],
        "coonhound": [],
        "corgi": [
            "cardigan"
        ],
        "cotondetulear": [],
        "dachshund": [],
        "dalmatian": [],
        "dane": [
            "great"
        ],
        "danish": [
            "swedish"
        ],
        "deerhound": [
            "scottish"
        ],
        "dhole": [],
        "dingo": [],
        "doberman": [],
        "elkhound": [
            "norwegian"
        ],
        "entlebucher": [],
        "eskimo": [],
        "finnish": [
            "lapphund"
        ],
        "frise": [
            "bichon"
        ],
        "gaddi": [
            "indian"
        ],
        "german": [
            "shepherd"
        ],
        "greyhound": [
            "indian",
            "italian"
        ],
        "groenendael": [],
        "havanese": [],
        "hound": [
            "afghan",
            "basset",
            "blood",
            "english",
            "ibizan",
            "plott",
            "walker"
        ],
        "husky": [],
        "keeshond": [],
        "kelpie": [],
        "kombai": [],
        "komondor": [],
        "kuvasz": [],
        "labradoodle": [],
        "labrador": [],
        "leonberg": [],
        "lhasa": [],
        "malamute": [],
        "malinois": [],
        "maltese": [],
        "mastiff": [
            "bull",
            "english",
            "indian",
            "tibetan"
        ],
        "mexicanhairless": [],
        "mix": [],
        "mountain": [
            "bernese",
            "swiss"
        ],
        "mudhol": [
            "indian"
        ],
        "newfoundland": [],
        "otterhound": [],
        "ovcharka": [
            "caucasian"
        ],
        "papillon": [],
        "pariah": [
            "indian"
        ],
        "pekinese": [],
        "pembroke": [],
        "pinscher": [
            "miniature"
        ],
        "pitbull": [],
        "pointer": [
            "german",
            "germanlonghair"
        ],
        "pomeranian": [],
        "poodle": [
            "medium",
            "miniature",
            "standard",
            "toy"
        ],
        "pug": [],
        "puggle": [],
        "pyrenees": [],
        "rajapalayam": [
            "indian"
        ],
        "redbone": [],
        "retriever": [
            "chesapeake",
            "curly",
            "flatcoated",
            "golden"
        ],
        "ridgeback": [
            "rhodesian"
        ],
        "rottweiler": [],
        "rough": [
            "collie"
        ],
        "saluki": [],
        "samoyed": [],
        "schipperke": [],
        "schnauzer": [
            "giant",
            "miniature"
        ],
        "segugio": [
            "italian"
        ],
        "setter": [
            "english",
            "gordon",
            "irish"
        ],
        "sharpei": [],
        "sheepdog": [
            "english",
            "indian",
            "shetland"
        ],
        "shiba": [],
        "shihtzu": [],
        "spaniel": [
            "blenheim",
            "brittany",
            "cocker",
            "irish",
            "japanese",
            "sussex",
            "welsh"
        ],
        "spitz": [
            "indian",
            "japanese"
        ],
        "springer": [
            "english"
        ],
        "stbernard": [],
        "terrier": [
            "american",
            "andalusian",
            "australian",
            "bedlington",
            "border",
            "boston",
            "cairn",
            "dandie",
            "fox",
            "irish",
            "kerryblue",
            "lakeland",
            "norfolk",
            "norwich",
            "patterdale",
            "russell",
            "scottish",
            "sealyham",
            "silky",
            "tibetan",
            "toy",
            "welsh",
            "westhighland",
            "wheaten",
            "yorkshire"
        ],
        "tervuren": [],
        "vizsla": [],
        "waterdog": [
            "spanish"
        ],
        "weimaraner": [],
        "whippet": [],
        "wolfhound": [
            "irish"
        ]
    },
    "status": "success"
}
It could be internalized, but then it won't be updated.
Sub-breeds are supported by the API, but for simplicity, ignore sub-breeds. If there is a need to get a specific breed composed of many sub-breeds, just use the parent breed (like terrier).

Finally, there's the feed by breed. This can be called the same way as the first feed call: https://dog.ceo/api/breed/hound/images/random/3. The last number is the number of images that will be returned.
This results in the following JSON format:
{
    "message": [
        "https://images.dog.ceo/breeds/hound-afghan/n02088094_4143.jpg",
        "https://images.dog.ceo/breeds/hound-basset/n02088238_10102.jpg",
        "https://images.dog.ceo/breeds/hound-ibizan/n02091244_3240.jpg"
    ],
    "status": "success"
}

Again, this returns image URLs, not images themselves. There is a need to acquire the image data from the URLs.
