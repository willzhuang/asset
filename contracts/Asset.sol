pragma solidity >=0.5.0 <0.6.0;

import 'libs/token/ERC721/ERC721Full.sol';
import 'libs/ownership/Ownable.sol';
import "libs/token/ERC721/ERC721Burnable.sol";
import "libs/token/ERC721/ERC721MetadataMintable.sol";

contract Asset is ERC721Full, Ownable, ERC721Burnable, ERC721MetadataMintable {
    constructor() ERC721Full("SHIET","IET") public {}

}
