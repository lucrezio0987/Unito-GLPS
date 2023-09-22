function showParty(num) {
    document.getElementById('party-1').style.display = 'none';
    document.getElementById('party-2').style.display = 'none';
    document.getElementById('party-3').style.display = 'none';
    
    document.getElementById('party-' + num).style.display = 'block';
}

function showPartyCharacters(party, num) {
    document.getElementById('p-1-party-'+ party).style.display = 'none';
    document.getElementById('p-2-party-'+ party).style.display = 'none';
    document.getElementById('p-3-party-'+ party).style.display = 'none';
    
    document.getElementById('p-' + num + '-party-' + party).style.display = 'block';
}